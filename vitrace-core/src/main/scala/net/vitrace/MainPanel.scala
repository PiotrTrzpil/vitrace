package net.vitrace

import scala.swing._

class MainPanel extends BoxPanel(Orientation.Vertical)
{
   def width = 1000
   def height = 600

   preferredSize = new Dimension(width, height)

   /*contents = {
     JMenuBar menubar = new JMenuBar();
      ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

      JMenu file = new JMenu("File");
      file.setMnemonic(KeyEvent.VK_F);

      JMenuItem eMenuItem = new JMenuItem("Exit", icon);
   }*/

   contents += new MenuBar()
   {
      contents += new Menu("File")
   }

   override def paintComponent(g:Graphics2D)
   {
      super.paintComponent(g)
   }


}
