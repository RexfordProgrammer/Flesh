package flesh;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Rexford
 */
public class CustomMouseListener implements MouseListener {
        public CustomMouseListener (){
            
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            // Add your logic for mouse click event
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Flesh.player.isFiring = true;

            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Flesh.player.isFiring = false;

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Add your logic for mouse enter event
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Add your logic for mouse exit event
        }
    }