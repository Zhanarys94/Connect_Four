import java.io.File                   // Import the File class for file handling
import javax.imageio.ImageIO          // Import the ImageIO class for reading and writing images
import java.awt.image.BufferedImage   // BufferedImage Class
import java.awt.Color

/*fun main() {
    val inputFile = File("C:\\Users\\janar\\Pictures\\Zhanarys Yeltayev.jpg")
    val myImage = ImageIO.read(inputFile)
    println("$myImage.width $myImage.height")
    for (x in 0 until myImage.width) {               // For every column.
        for (y in 0 until myImage.height) {          // For every row
            val color = Color(myImage.getRGB(x, y))  // Read color from the (x, y) position

            val r = color.red              // Access the Red color value
            val g = color.green              // Access the Green color value
            val b = color.blue               // Access the Blue color value

            val colorNew = Color(255, g, b)  // Create a new Color instance with the red value equal to 255
            myImage.setRGB(x, y, colorNew.rgb)  // Set the new color at the (x, y) position
        }
    }
    val outputFileJpg = File("C:\\Users\\janar\\Pictures\\Zhanarys Yeltayev111.jpg")  // Output the file
    ImageIO.write(myImage, "jpg", outputFileJpg)  // Create an image using the BufferedImage instance data
}*/

fun main() {
    val inputFile = File("C:\\Users\\janar\\Pictures\\Zhanarys Yeltayev.jpg")
    val myImage: BufferedImage = ImageIO.read(inputFile)
    for (x in 0 until myImage.width) {
        for (y in 0 until myImage.height) {
            val color = Color(myImage.getRGB(x, y))
            val colorNew = Color(255 - color.red, 255 - color.green, 255 - color.blue)
            myImage.setRGB(x, y, colorNew.rgb)
        }
    }
    val outputFileJpg = File("C:\\Users\\janar\\Pictures\\Zhanarys Yeltayev111.jpg")  // Output the file
    ImageIO.write(myImage, "jpg", outputFileJpg)
}