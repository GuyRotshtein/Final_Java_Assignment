import Model.DBConnection;
import Model.DBConnection2;
import View.MainFrame;

public class Main {
    public static void main(String[] args)
    {

<<<<<<< Updated upstream
    		//DBConnection.connectDB();
            DBConnection2 dbConnect = new DBConnection2();
            dbConnect.go(args);
    		MainFrame mf = new MainFrame();
=======
            ViewModel vm = new ViewModel(args);
>>>>>>> Stashed changes
		
    	
    	
//    	   // Create a HashMap
//        HashMap<String , Integer > map = new HashMap<>();
//        map.put("catagory",1);
//        map.put("Sum",2 );
//        map.put("Currency",3 );
//
//        // Convert HashMap to Object[][]
//        Object[][] data = new Object[map.size()][2];
//        int i = 0;
//        for (HashMap.Entry<String, Integer> entry : map.entryset() ) 
//        {
//            data[i][0] = entry.getKey();
//            data[i][1] = entry.getValue();
//            i++;
//        }
//    	
//
//        MainFrame frame = new MainFrame(data);
    	
    	
        
    }
}