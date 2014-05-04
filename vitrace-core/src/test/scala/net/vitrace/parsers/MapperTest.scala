package net.vitrace.parsers

import scala.language.experimental.macros
import org.scalatest.FunSuite
import net.vitrace.parsers.mapper.{ParseLineState, Mapper}


// This imports the basic constructors and predefined patterns.

import scala.util.parsing.combinator.RegexParsers
// This imports a single (as of Rex 0,7) implicit conversion that allows strings to be used
// as literals in Rex expressions.
//import com.digitaldoodles.rex.Implicits._
// This imports objects that contain further predefined patterns; see the API documentation for details.

class MapperTest extends FunSuite with RegexParsers
{

  test("t2")
  {

    val spring = new SpringCustomParser
    val tomcat = new TomcatParser
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
    val parsers: Stream[LogParser] = Stream(spring, tomcat, LogParser.any)
    val mapper = new Mapper(parsers)
    val res = lines.map(mapper.map)


    println(res.map(s => s.groups.head.parser))

    val red = res.reduce(mapper.reduce)
    println(red.parserLineCounts)


  }

}