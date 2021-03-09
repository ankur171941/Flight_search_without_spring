package org.example;

import java.io.File;
import java.io.IOException;

public class Resolver implements Runnable{
    public void fileReader() throws IOException
    {
        File file = new File("C:\\Users\\HP\\Downloads\\ecommerce_ankur_1710991941-master\\Flight_book\\.idea\\files");
//System.out.println(file.list());
        if(file.exists()) {
            String arr[] = file.list();
            for (int i = 0; i < arr.length; i++) {
                FlightController.addData("C:\\Users\\HP\\Downloads\\ecommerce_ankur_1710991941-master\\Flight_book\\.idea\\files\\"+arr[i]);
                //System.out.println(arr[i]);
            }

        }
        else
            System.out.println("Directory not found");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            fileReader();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
