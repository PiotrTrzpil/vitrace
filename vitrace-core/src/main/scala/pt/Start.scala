package pt

import java.awt.Dimension
import scala.swing
import scala.swing
import scala.swing.{MainFrame, SwingApplication}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.ApplicationContext


object Start extends SwingApplication
{

   private val logger: Logger = LoggerFactory.getLogger(Start.getClass)



   private val mainPanel = new MainPanel
   private val controller = new MainController(mainPanel)

   private def top = new MainFrame
   {
      title = "Charactor Simulation"
      contents = mainPanel;
      size = new Dimension(1010, 610)
   }

   override def startup(args: Array[String])
   {

      logger.debug("starting the app");
    //  controller.start();
    //  mainPanel.beginDrawing();



      val applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");

      logger.info("Spring context initialized.");

      val config = applicationContext.getBean("myConfiguration").asInstanceOf[MyConfiguration];
      val tet = applicationContext.getBean("tet").asInstanceOf[Tet];
      tet.sds()
      config.gdad()
      //applicationContext.getBean();
  //    Message message = (Message) applicationContext.getBean("message");

    //  logger.debug("message='" + message.getMessage() + "'");
      som()

      val mainController = new MainController(null)
      
      top.pack()
      top.visible = true

      mainController.run()
   }

   def som()
   {
      logger.trace("som")

   }

}
