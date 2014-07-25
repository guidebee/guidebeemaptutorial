using Gtk;


namespace GTKMapDemo
{
    class GuidebeeMapDemo 
    {
       
        static void Main()
        {
            Application.Init();
            new GuidebeeMapDemo();
            Application.Run();
        }

        
		GuidebeeMapDemo(){
			Window win = new MainWindow();
			win.ShowAll();
          
		}
		
       
    
    }
}