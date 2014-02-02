package net.vitrace

import scala.swing._
import java.awt.Color
import scala.swing.Dimension
import net.vitrace.parsers.LogEntry

class MainPanel extends BoxPanel(Orientation.Vertical)
{
   def width = 400
   def height = 600

   background = Color.BLUE
   preferredSize = new Dimension(width, height)

   /*contents = {
     JMenuBar menubar = new JMenuBar();
      ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

      JMenu file = new JMenu("File");
      file.setMnemonic(KeyEvent.VK_F);

      JMenuItem eMenuItem = new JMenuItem("Exit", icon);
   }*/

 //  contents +=

   override def paintComponent(g:Graphics2D)
   {
      super.paintComponent(g)
   }

   def createBox( e : LogEntry)
   {
      contents += new EntryBox
      contents += new Button("sds")
   }
}
