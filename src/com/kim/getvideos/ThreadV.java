package com.kim.getvideos;

public class ThreadV  extends Thread{

    
    public ThreadV(String name){ 
        super(name); 
    }
    
    public synchronized void run() { 
       try {
           for(int i=0; i <10; i++){ 
               System.out.printf("%s: %d\t", this.getName(), i);                
               // i�ܱ�4����ʱ������500����
               if (i%4 == 0)//if (i%4 == 0)
                   Thread.sleep(500);
           }

           System.out.printf("\n");
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
  }