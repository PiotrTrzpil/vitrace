package pt

import java.awt.{Color, Dimension, Graphics2D}
import javax.swing.SwingUtilities
import java.util.concurrent.{Semaphore, LinkedBlockingQueue, BlockingQueue}
import scala.swing.Panel

class MainPanel extends Panel
{
   def width = 1000;
   def height = 600;

   preferredSize = new Dimension(width, height);


   override def paintComponent(g:Graphics2D)
   {
      super.paintComponent(g);
   }


}