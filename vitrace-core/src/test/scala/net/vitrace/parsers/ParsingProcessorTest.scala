package net.vitrace.parsers

import scala.language.experimental.macros
import org.scalatest.FunSuite
import net.vitrace.parsers.mapper.{RawLinesMerger, LineGroup, ParseLineState, ParsingProcessor}
import org.apache.spark.SparkContext
import java.io.{FileOutputStream, File, ObjectOutputStream}
import scala.pickling._
import json._


// This imports the basic constructors and predefined patterns.

import scala.util.parsing.combinator.RegexParsers
import scala.io.Source

class ParsingProcessorTest extends FunSuite with RegexParsers
{

  test("basic e2e test")
  {

    val lines: List[String] = List(
      "INFO  15:18:14.797 o.s.s.c.SpringSecurityCoreVersion [ip:123] [user:dd] - You are running with Spring Security Core 3.1.4.RELEASE",
      "sie 09, 2013 5:16:45 PM org.apache.catalina.startup.HostConfig deleteRedeployResources",
      "INFO: Undeploying context [/cms-front]",
      "INFO  17:16:45.481 o.s.w.c.s.XmlWebApplicationContext [ip:] [user:] - Closing WebApplicationContext for namespace 'blinkycms-servlet'",
      "17:16:45,481 |-INFO in ch.qos.logback.classic.net.SMTPAppender[email] - SMTPAppender [email] is tracking [1] buffers",
      "INFO  17:16:45.483 o.s.b.f.s.DefaultListableBeanFactory [ip:] [user:] - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@34bab34: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor]",
      "INFO  17:16:45.502 o.s.w.c.s.XmlWebApplicationContext [ip:] [user:] - Closing Root WebApplicationContext: startup date [Fri Aug 09 10:09:31 CEST 2013]; root of context hierarchy",
      "INFO  17:16:45.503 o.s.b.f.s.DefaultListableBeanFactory [ip:] [user:] - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@193e7bb: ",
      "INFO  17:16:45.503 o.s.j.e.MBeanExporter [ip:] [user:] - Unregistering JMX-exposed beans on shutdown",
      "sie 09, 2013 5:16:45 PM org.apache.catalina.loader.WebappClassLoader clearReferencesJdbc",
      "SEVERE: The web application [/cms-front] registered the JDBC driver [com.mysql.jdbc.Driver] but failed to unregister it."
    )
    val spring = new SpringCustomParser
    val tomcat = new TomcatParser
    val parsers = Stream(()=>new SpringCustomParser, ()=>new TomcatParser, ()=>new RawFallbackParser)
    val mapper = new ParsingProcessor(parsers)
    val res = lines.map(mapper.map)


 //  println(res.zipWithIndex.map{case (groups, i) => (1+i)+": "+groups.head.parser+"\n"}.mkString)

    val red = res.reduce(mapper.reduce)
  //  println(parserLineCounts(red).map{case (p, count) => s"$p:$count"+("\n"*count)}.mkString)
  }

  test("file e2e test")
  {

    val file = "C:\\PLIKI\\Programowanie\\Scala\\vitrace-testy\\catalina-small.txt"
    time(doParse(file))
  }

  def doParse(file:String) = {
    val lines = Source.fromFile(file).getLines().toVector

    val spring = new SpringCustomParser
    val tomcat = new TomcatParser
    val parsers = Stream(()=>new SpringCustomParser, ()=>new TomcatParser, ()=>new RawFallbackParser)
    //val parsers: Stream[LogParser] = Stream(spring, tomcat, LogParser.any)
    val mapper = new ParsingProcessor(parsers)


    val res: Vector[Vector[LineGroup]] = lines.map(mapper.map)
 //   res.pickle
   // println(res.zipWithIndex.map{case (s, i) => (1+i)+": "+s.head.parser+"\n"}.mkString)
  //  new ObjectOutputStream(new FileOutputStream("C:\\PLIKI\\ttt.txt")).writeObject(res)
    val red = res.reduce(mapper.reduce)
   // println(parserLineCounts(red).map{case (p, count) => s"$p:$count"+("\n"*count)}.mkString)

    val merger = new RawLinesMerger()
    val red2 = red.map(merger.map).reduce(merger.reduce)
   // println(parserLineCounts(red2).map{case (p, count) => s"$p:$count"+("\n"*count)}.mkString)

  }

  def parserLineCounts(groups:Vector[LineGroup]) = groups.map(g =>(g.parser, g.logLines.length))

  def time[R](block: => R): R = {
     val t0 = System.nanoTime()
     val result = block
     println("Elapsed time: " + (System.nanoTime - t0) + "ns")
     result
  }

  var sc: SparkContext = _

  test("spark filter") {

    sc = new SparkContext("local[4]", "test mapreduce")
    time {
      try {
        val file = "C:\\PLIKI\\Programowanie\\Scala\\vitrace-testy\\catalina.txt"
        val lines = sc.textFile(file)//Source.fromFile(file).getLines().toVector

        val spring = new SpringCustomParser
        val tomcat = new TomcatParser
       // val parsers: Stream[LogParser] = Stream(spring, tomcat, LogParser.any)
       val parsers = Stream(()=>new SpringCustomParser, ()=>new TomcatParser, ()=>new RawFallbackParser)
        val mapper = new ParsingProcessor(parsers)
        val merger = new RawLinesMerger()
        val a = lines.map(mapper.map).reduce(mapper.reduce)

        a.map(merger.map).reduce(merger.reduce)
      }
      finally {
        sc.stop()
        sc = null
        // To avoid Akka rebinding to the same port, since it doesn't unbind immediately on shutdown
        System.clearProperty("spark.master.port")

      }
    }


    // println(res.zipWithIndex.map{case (s, i) => (1+i)+": "+s.head.parser+"\n"}.mkString)

    // println(parserLineCounts(red).map{case (p, count) => s"$p:$count"+("\n"*count)}.mkString)


    // println(parserLineCounts(red2).map{case (p, count) => s"$p:$count"+("\n"*count)}.mkString)

  }


  def sparkTest(name: String, silenceSpark : Boolean = true)(body: => Unit) {
    test(name){

      sc = new SparkContext("local[4]", name)
      try {
        body
      }
      finally {
        sc.stop()
        sc = null
        // To avoid Akka rebinding to the same port, since it doesn't unbind immediately on shutdown
        System.clearProperty("spark.master.port")

      }
    }
  }

}