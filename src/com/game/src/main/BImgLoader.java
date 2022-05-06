    package com.game.src.main;

    import javax.imageio.ImageIO;
    import java.awt.image.BufferedImage;
    import java.io.IOException;

    public class BImgLoader {
        private BufferedImage img;
        public BufferedImage loadImage(String pathToImg) throws IOException {
            img = ImageIO.read(getClass().getResource(pathToImg));
            return img;
        }
    }
