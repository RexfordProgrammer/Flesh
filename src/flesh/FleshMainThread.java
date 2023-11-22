/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flesh;

public class FleshMainThread extends Thread {
    int refreshRate = 10;
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        while (true) {
            Flesh.fleshInstance.repaint();
            Flesh.fleshInstance.revalidate();
            
            try {
                Thread.sleep(refreshRate);
            } catch (InterruptedException v) {
                System.out.println(v);
            }
        }
    }
}
