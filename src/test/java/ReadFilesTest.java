import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadFilesTest {

    private ClassLoader classLoader = ReadFilesTest.class.getClassLoader();

    @Test
    @Description("Проверка столбцов в excel файле графика отпусков")
    void readExcelFromZipTest() throws Exception {
        try (InputStream files = classLoader.getResourceAsStream("files.zip");
             ZipInputStream zs = new ZipInputStream(files)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("graph.xls")) {
                    XLS xls = new XLS(zs);
                    System.out.println("");
                    Assertions.assertTrue(
                            xls.excel.getSheetAt(0).getRow(14).getCell(0).getStringCellValue()
                                        .startsWith("Структурное подразделение") &&
                                    xls.excel.getSheetAt(0).getRow(14).getCell(20).getStringCellValue()
                                        .startsWith("Должность (специальность, профессия)") &&
                                    xls.excel.getSheetAt(0).getRow(14).getCell(40).getStringCellValue()
                                        .startsWith("Фамилия, имя, отчество")
                    );
                }
            }
        }
    }

    @Test
    @Description("Проверка первой строки списка городов в CSV файле")
    void readCsvFromZip()throws Exception {
        try (InputStream files = classLoader.getResourceAsStream("files.zip");
             ZipInputStream zs = new ZipInputStream(files)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("cities.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> string = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[] {"   41","    5","   59", "N","     80","   39","    0", "W", "Youngstown"," OH"}, string.get(1));
                }
            }
        }
    }

    @Test
    @Description("Проверка значения поля creator pdf файле")
    @Tag("PDF")
    void readTitleInPdfFromZip() throws Exception{
        try (InputStream files = classLoader.getResourceAsStream("files.zip");
             ZipInputStream zs = new ZipInputStream(files)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().equals("sample.pdf")) {
                    PDF pdf = new PDF(zs);
                    System.out.println("");
                    Assertions.assertEquals("Rave (http://www.nevrona.com/rave)", pdf.creator);
                }
            }
        }

    }
}