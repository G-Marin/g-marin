import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
 
public class SeamCarver {
    private Picture seamPic; // Current Picture Object
 
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
  
        if (picture == null) {
            throw new IllegalArgumentException();
        }
 
        seamPic = new Picture(picture);
    }
 
    // current picture
    public Picture picture() {
 
        Picture pic = new Picture(seamPic); 
        return pic; 
    }
 
    // width of current picture
    public int width() {
        return seamPic.width();
    }
 
    // height of current picture
    public int height() {
        return seamPic.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // energy of pixel at column x and row y 
 
        if (x < 0 || y < 0 || x > width() || y > height()) {
            throw new IllegalArgumentException();
        }
 
        // prevRow= (row+height-1)%seamPic.height(); 
        double xGradSqr;
        double yGradSqr;
        // Look to coordinates x-1, x+1, y-1, y+1
        // Use get.RGB on each one
        int nextX = seamPic.getRGB(nextXCoord(x), y);
        int prevX = seamPic.getRGB(prevXCoord(x), y);
        int nextY = seamPic.getRGB(x, nextYCoord(y));
        int prevY = seamPic.getRGB(x, prevYCoord(y));
        // Use formulas to get Red Green and Blue
 
        // Subtract opposing coordinates(Red, Green, Blue values of x+1,
        xGradSqr = centralDif(nextX, prevX);
        yGradSqr = centralDif(nextY, prevY);
        // That is delta x
        // Finish formula
        // Square root of delta x squared + delta y squared
        double energy = Math.sqrt(xGradSqr + yGradSqr);
        return energy;
 
    }
 
    // Returns difference in RGB values between two pixels
    private double centralDif(int a, int b) {
 
        int aRed = (a >> 16) & 0xFF;
        int aGreen = (a >> 8) & 0xFF;
        int aBlue = (a) & 0xFF;
        int bRed = (b >> 16) & 0xFF;
        int bGreen = (b >> 8) & 0xFF;
        int bBlue = (b) & 0xFF;
 
        int redSub = aRed - bRed;
        int greenSub = aGreen - bGreen;
        int blueSub = aBlue - bBlue;
 
        return Math.pow(redSub, 2) + Math.pow(greenSub, 2) + Math.pow(blueSub, 2);
    }
 
    // Returns y value of pixel below current pixel 
    private int prevYCoord(int coord) { 
        return (coord + seamPic.height() - 1) % seamPic.height(); 
    } 
  
    // Returns y value of pixel above current pixel 
    private int nextYCoord(int coord) { 
        return (coord + seamPic.height() + 1) % seamPic.height(); 
    } 
  
    // Returns x value of pixel to the rigth of current pixel 
    private int nextXCoord(int coord) { 
        return (coord + 1 + seamPic.width()) % seamPic.width(); 
    } 
  
    // Returns x value of pixel to the left of current pixel 
    private int prevXCoord(int coord) { 
        return (coord - 1 + seamPic.width()) % seamPic.width(); 
    } 
 
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
 
        seamPic = tranpose(seamPic);
        int[] horizontalSeam = findVerticalSeam();
        seamPic = tranpose(seamPic); 
        return horizontalSeam;
 
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() { 
 
        int height = height();
        int width = width();
 
        double[][] distance = new double[height][width];
        int[][] edgeTo = new int[height][width];
        double[][] energyMatrix = new double[height][width];
        int[] seam = new int[height];
 
 
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
 
                energyMatrix[i][j] = energy(j, i);
 
                if (i == 0) {
                    distance[i][j] = energyMatrix[i][j];
                    edgeTo[i][j] = j;
                }
                else {
                    distance[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
 
 
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
 
                if (j - 1 >= 0 && distance[i + 1][j - 1] > distance[i][j] +
                        energyMatrix[i + 1][j - 1]) {
 
                    distance[i + 1][j - 1] = distance[i][j] +
                            energyMatrix[i + 1][j - 1];
 
                    edgeTo[i + 1][j - 1] = j;
                }
 
 
                if (distance[i + 1][j] > distance[i][j] +
                        energyMatrix[i + 1][j]) {
 
                    distance[i + 1][j] = distance[i][j] +
                            energyMatrix[i + 1][j];
 
                    edgeTo[i + 1][j] = j;
                }
 
                if (j + 1 < width && distance[i + 1][j + 1] >
                        distance[i][j] + energyMatrix[i + 1][j + 1]) {
 
                    distance[i + 1][j + 1] = distance[i][j] +
                            energyMatrix[i + 1][j + 1];
 
                    edgeTo[i + 1][j + 1] = j;
                }
            }
        }
 
 
        int champion = 0;
        int j = height - 1;
        double championDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width; i++) {
 
            if (distance[j][i] < championDistance) {
                championDistance = distance[j][i];
                champion = i;
            }
        }
 
 
        for (int i = height - 1; i >= 0; i--) {
 
            seam[i] = champion;
            champion = edgeTo[i][champion];
 
        }
 
        return seam;
    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
 
 
        if (seam == null || height() == 1 || seam.length != width()) {
            throw new IllegalArgumentException();
        }
 
        for (int i = 0; i < seam.length; i++) {
            if (i == seam.length - 1) {
                if (seam[i] > height() - 1 || seam[i] < 0) {
                    throw new IllegalArgumentException("");
                }
                break;
            }
 
            if (Math.abs(seam[i] - seam[i + 1]) > 1 || seam[i] > height() - 1 ||
                    seam[i] < 0) {
                throw new IllegalArgumentException();
            }
        }
 
 
        seamPic = tranpose(seamPic);
        removeVerticalSeam(seam);
        seamPic = tranpose(seamPic);
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
 
        if (seam == null || seam.length != height() || width() == 1) {
            throw new IllegalArgumentException();
        }
 
        for (int i = 0; i < seam.length; i++) {
 
            if (i == seam.length - 1) {
                if (seam[i] > width() - 1 || seam[i] < 0) {
                    throw new IllegalArgumentException();
                }
                break;
            }
 
            if (Math.abs(seam[i] - seam[i + 1]) > 1 || seam[i] > width() - 1 ||
                    seam[i] < 0) {
                throw new IllegalArgumentException();
            }
        }
 
 
        int width = width() - 1;
        Picture npic = new Picture(width, height());
        int check = 0; 
 
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width; j++) {
 
                if (j == seam[i] || check > 0) {
                    check += 1; 
                    npic.setRGB(j, i, seamPic.getRGB(j + 1, i));
                }
                else {
                    npic.setRGB(j, i, seamPic.getRGB(j, i));
                }
            }
            check = 0;
        }
        seamPic = npic;
    }
 
 
    // Returns tranposed picture
    private Picture tranpose(Picture photo) {
 
        Picture tPhoto = new Picture(photo.height(), photo.width());
        for (int i = 0; i < tPhoto.height(); i++) {
            for (int j = 0; j < tPhoto.width(); j++) {
                tPhoto.setRGB(j, i, photo.getRGB(i, j));
            }
        }
        return tPhoto;
    }
 
 
    //  unit testing (required)
    public static void main(String[] args) {
 
        Picture pic = new Picture(args[0]);
        SeamCarver carverPic = new SeamCarver(pic);
 
        StdOut.print("Energy of Pixel at (0,0): " + carverPic.energy(0, 0));
        StdOut.print("Height of Picture: " + carverPic.height());
        StdOut.print("Width of Picture: " + carverPic.width());
 
        int[] horizontalSeam = carverPic.findHorizontalSeam();
        int[] verticalSeam = carverPic.findVerticalSeam();
 
        carverPic.removeHorizontalSeam(horizontalSeam);
        carverPic.removeVerticalSeam(verticalSeam);
 
        StdOut.print("Picture after removing horizontal & vertical seam: " +
                             carverPic.picture());
 
    } 
 
} 
