import Model.DBConnection;
import Model.DBConnection2;
import ViewVmodel.ViewModel;
import View.MainFrame;

public class Main {

    public static void main(String[] args)
    {


    		//DBConnection.connectDB();
            DBConnection2 dbConnect = new DBConnection2(args);
            dbConnect.go(args);
            DBConnection simConnect = new DBConnection();
            simConnect.connectDB();

    		//MainFrame mf = new MainFrame();
            ViewModel vm = new ViewModel(args);

		
    	
    	
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