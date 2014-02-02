package net.vitrace

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.context.support.ClassPathXmlApplicationContext
import scala.swing._
import scala.swing.event.ButtonClicked
import javax.swing.UIManager


object Start extends SwingApplication
{

   private val logger: Logger = LoggerFactory.getLogger(Start.getClass)



   private val mainPanel = new MainPanel
   private val controller = new MainController(mainPanel)
   private def top = new MainFrame
   {
      title = "Vitrace"
      contents = mainPanel
      size = new Dimension(1010, 610)
      menuBar = new MenuBar()
      {
         contents += new Menu("File")
         {
            contents += new MenuItem("Open...")
            {
               reactions += {
                  case ButtonClicked(event) =>
                     controller.openFile()
               }
            }
         }
      }
   }

   override def startup(args: Array[String])
   {

      logger.debug("starting the app")
    //  controller.start();
    //  mainPanel.beginDrawing();

      UIManager.setLookAndFeel(
         UIManager.getSystemLookAndFeelClassName)

      val applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml")

      logger.info("Spring context initialized.")

      //val config = applicationContext.getBean("myConfiguration").asInstanceOf[MyConfiguration];
      val tet = applicationContext.getBean("tet").asInstanceOf[Tet]
      tet.sds()
    //  config.gdad()
      //applicationContext.getBean();
  //    Message message = (Message) applicationContext.getBean("message");

    //  logger.debug("message='" + message.getMessage() + "'");
      som()


      top.pack()
      top.visible = true

      controller.run()
   }

   def som()
   {
      logger.trace("som")

   }

}
