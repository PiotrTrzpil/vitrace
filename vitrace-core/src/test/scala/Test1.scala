import net.vitrace.parsers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

import scala.language.experimental.macros


// This imports the basic constructors and predefined patterns.
import com.digitaldoodles.rex._

import scala.util.parsing.combinator.RegexParsers
// This imports a single (as of Rex 0,7) implicit conversion that allows strings to be used
// as literals in Rex expressions.
//import com.digitaldoodles.rex.Implicits._
// This imports objects that contain further predefined patterns; see the API documentation for details.

@RunWith(classOf[JUnitRunner])
class Test1 extends FunSuite with RegexParsers
{


   test("t2")
   {
     // val s = new Log4NetStandardParser()
      val o = new SpringCustomParser
      val any = new AnyParser()
      val lines: List[String] = List("DEBUG [1] - 2013-10-24 19:44:23,305  1730ms Infrastructure.TraceAttribute sdnadnas",
         "INFO  15:18:14.797 o.s.s.c.SpringSecurityCoreVersion [ip:123] [user:dd] - You are running with Spring Security Core 3.1.4.RELEASE",
      "sie 09, 2013 5:16:45 PM org.apache.catalina.startup.HostConfig deleteRedeployResources",
"INFO: Undeploying context [/cms-front]",
"INFO  17:16:45.481 o.s.w.c.s.XmlWebApplicationContext [ip:] [user:] - Closing WebApplicationContext for namespace 'blinkycms-servlet': startup date [Fri Aug 09 10:09:33 CEST 2013]; parent: Root WebApplicationContext",
"17:16:45,481 |-INFO in ch.qos.logback.classic.net.SMTPAppender[email] - SMTPAppender [email] is tracking [1] buffers",
"INFO  17:16:45.483 o.s.b.f.s.DefaultListableBeanFactory [ip:] [user:] - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@34bab34: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,VCService,loginController,statsController,adminController,trackableController,campaignController,fileController,applicationController,contentController,ffmpegVideoConverter,fileService,pathProvider,trackableService,urlProvider,metadataCreator,idGenerator,VCProcessingService,imageResizer,routes,clientDAO,campaignDAO,webUserDAO,processingEventDAO,datasetDAO,applicationDAO,contentDAO,billingDAO,trackableDAO,authSuccessHandler,permissionChecker,annotatedExceptionResolver,ajaxAwareLoginUrlAuthenticationEntryPoint,blinkyUserDetailsService,appListener,blinkyPermissionEvaluator,customExceptionResolver,mapperService,customHandlerInterceptor,mvcContentNegotiationManager,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#0,org.springframework.format.support.FormattingConversionServiceFactoryBean#0,org.springframework.validation.beanvalidation.LocalValidatorFactoryBean#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#0,org.springframework.web.servlet.handler.MappedInterceptor#0,org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver#0,org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#0,org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#0,org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,org.springframework.web.servlet.handler.MappedInterceptor#1,org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#0,org.springframework.security.filterChains,org.springframework.security.filterChainProxy,org.springframework.security.web.PortMapperImpl#0,org.springframework.security.web.PortResolverImpl#0,org.springframework.security.authentication.ProviderManager#0,org.springframework.security.web.context.HttpSessionSecurityContextRepository#0,org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy#0,org.springframework.security.web.savedrequest.HttpSessionRequestCache#0,org.springframework.security.access.vote.AffirmativeBased#0,org.springframework.security.web.access.intercept.FilterSecurityInterceptor#0,org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator#0,org.springframework.security.authentication.AnonymousAuthenticationProvider#0,org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices#0,org.springframework.security.authentication.RememberMeAuthenticationProvider#0,org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint#0,org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#0,org.springframework.security.userDetailsServiceFactory,org.springframework.security.web.DefaultSecurityFilterChain#0,org.springframework.security.authentication.dao.DaoAuthenticationProvider#0,org.springframework.security.authentication.DefaultAuthenticationEventPublisher#0,authenticationManager,rememberMeFilter,rememberMeServices,rememberMeAuthenticationProvider,filterChainProxy,handlerMapping,templateResolver,templateEngine,viewResolver,multipartResolver,metricRegistry,validator,customizableTraceInterceptor,org.springframework.aop.config.internalAutoProxyCreator,org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#1,propertyConfigurer,org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor]; parent: org.springframework.beans.factory.support.DefaultListableBeanFactory@193e7bb",
"INFO  17:16:45.502 o.s.w.c.s.XmlWebApplicationContext [ip:] [user:] - Closing Root WebApplicationContext: startup date [Fri Aug 09 10:09:31 CEST 2013]; root of context hierarchy",
"INFO  17:16:45.503 o.s.b.f.s.DefaultListableBeanFactory [ip:] [user:] - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@193e7bb: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,VCService,loginController,statsController,adminController,trackableController,campaignController,fileController,applicationController,contentController,ffmpegVideoConverter,fileService,pathProvider,trackableService,urlProvider,metadataCreator,idGenerator,VCProcessingService,imageResizer,routes,clientDAO,campaignDAO,webUserDAO,processingEventDAO,datasetDAO,applicationDAO,contentDAO,billingDAO,trackableDAO,authSuccessHandler,permissionChecker,annotatedExceptionResolver,ajaxAwareLoginUrlAuthenticationEntryPoint,blinkyUserDetailsService,appListener,blinkyPermissionEvaluator,customExceptionResolver,mapperService,customHandlerInterceptor,mvcContentNegotiationManager,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping#0,org.springframework.format.support.FormattingConversionServiceFactoryBean#0,org.springframework.validation.beanvalidation.LocalValidatorFactoryBean#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#0,org.springframework.web.servlet.handler.MappedInterceptor#0,org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver#0,org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#0,org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#0,org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,org.springframework.web.servlet.handler.MappedInterceptor#1,org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#0,org.springframework.security.filterChains,org.springframework.security.filterChainProxy,org.springframework.security.web.PortMapperImpl#0,org.springframework.security.web.PortResolverImpl#0,org.springframework.security.authentication.ProviderManager#0,org.springframework.security.web.context.HttpSessionSecurityContextRepository#0,org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy#0,org.springframework.security.web.savedrequest.HttpSessionRequestCache#0,org.springframework.security.access.vote.AffirmativeBased#0,org.springframework.security.web.access.intercept.FilterSecurityInterceptor#0,org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator#0,org.springframework.security.authentication.AnonymousAuthenticationProvider#0,org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices#0,org.springframework.security.authentication.RememberMeAuthenticationProvider#0,org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint#0,org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter#0,org.springframework.security.userDetailsServiceFactory,org.springframework.security.web.DefaultSecurityFilterChain#0,org.springframework.security.authentication.dao.DaoAuthenticationProvider#0,org.springframework.security.authentication.DefaultAuthenticationEventPublisher#0,authenticationManager,rememberMeFilter,rememberMeServices,rememberMeAuthenticationProvider,filterChainProxy,handlerMapping,templateResolver,templateEngine,viewResolver,multipartResolver,metricRegistry,validator,customizableTraceInterceptor,org.springframework.aop.config.internalAutoProxyCreator,org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#1,propertyConfigurer,exporter,jmxAttributeSource,assembler,namingStrategy,flyway,dataSource,org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor]; root of factory hierarchy",
"INFO  17:16:45.503 o.s.j.e.MBeanExporter [ip:] [user:] - Unregistering JMX-exposed beans on shutdown",
"sie 09, 2013 5:16:45 PM org.apache.catalina.loader.WebappClassLoader clearReferencesJdbc",
"SEVERE: The web application [/cms-front] registered the JDBC driver [com.mysql.jdbc.Driver] but failed to unregister it when the web application was stopped. To prevent a memory leak, the JDBC Driver has been forcibly unregistered."
)
      val l: List[LogParser] = List(o, LogParser.any)
      val builder = new EntriesBuilder
      println(builder.parseText(lines, l))


   }

   case class Line4(message : String) extends LogLine

   test("asMap")
   {
      val line = Line4("tada")
      val map = line.asMap
      assert(map.isEmpty === false)

   }
   test("t22")
   {
     val e1 = new LogEntry()
      e1.logLines += new Line4("tada")
      e1.logLines += new Line4("rgsfse")
      val e2 = new LogEntry()
      e2.logLines += new Line4("tada")
      e2.logLines += new Line4("rgsfse")
     val entries = List()
   }
   test("t3")
   {
      // *>1 means 1 or more, greedily (as many times as possible.)
      val posInt = CharRange('0','9')*>1
      // Lit means literal. The "-" is automatically converted to a literal.
      val sign = Lit("+")|Lit("-")
      // +~ represents concatenation, ? means an element is optional.
      val floatPat = sign.? +~ posInt +~ (Lit(".") +~ posInt).?
      // "name" creates named groups for extracting information from matches.
      val complex = floatPat.name("re") +~ sign.name("op") +~ floatPat.name("im") +~ Lit("i")

   /*   println(complex.r)
      val res: Boolean = complex ~~= "-423.34+342i"
      println(res)*/



   }
}