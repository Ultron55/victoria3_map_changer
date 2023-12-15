import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.ImageData
import org.eclipse.swt.graphics.ImageLoader
import org.json.JSONArray
import java.awt.Color
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    try {
        val fis = FileInputStream("impassable.json")
        val bytes = fis.readAllBytes()
        fis.close()
        val colorsHex = JSONArray(String(bytes, StandardCharsets.UTF_8))
        println("write file name:")
        val scanner = Scanner(System.`in`)
        val filename = scanner.nextLine()
        val alphaImpassable: Int
        val alphaBack: Int
        while (true) {
            println("impassable in transparent? yes/no")
            val result = scanner.nextLine()
            if (result == "yes") {
                alphaImpassable = 0
                alphaBack = 255
                break
            } else if (result == "no") {
                alphaImpassable = 255
                alphaBack = 0
                break
            }
        }
        println("Start ...")
        val imageData = ImageData(filename)
        val image = ImageIO.read(File("provinces.png"))
        val width = image.width
        val height = image.height
        val colorsList = ArrayList<Int>()
        for (i in 0 until colorsHex.length()) {
            colorsList.add(Color.decode(colorsHex.getString(i)).rgb)
        }
        colorsHex.clear()
        var color: Int
        for (i in 0 until width) {
            for (j in 0 until height) {
                color = image.getRGB(i, j)
                if (colorsList.contains(color)) {
                    imageData.setAlpha(i, j, alphaImpassable)
                } else {
                    imageData.setAlpha(i, j, alphaBack)
                }
            }
            if (i % 1000 == 0) println(i)
        }
        val imageLoader = ImageLoader()
        imageLoader.data = arrayOf(imageData)
        imageLoader.save("impassable_mask.png", SWT.IMAGE_PNG)
    } catch (e: Exception) {
        println("Error" + e.message)
    }
}