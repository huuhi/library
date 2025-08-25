package zhijianhu.libraryserver;

import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhijianhu.entity.Books;
import zhijianhu.libraryserver.service.BooksService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;

/**
 * @author 胡志坚
 * @version 1.0
 * 创造日期 2025/8/19
 * 说明:
 */
@SpringBootTest
public class SaveBooks {
    @Autowired
    private BooksService booksService;
    @Test
    void saveOtherBooks(){
        StringBuilder content= new StringBuilder();
        try (Reader fr = new FileReader("D:\\Python\\develop\\Review_Python\\Demo02\\resources\\books.json");
             BufferedReader frs=new BufferedReader(fr);
        ) {
            String line;
            while((line=frs.readLine())!=null){
//                System.out.println(line);
                content.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        List<Books> list = JSONUtil.toList(content.toString(), Books.class);
        for (Books book : list) {
            if (book.getPublishDateStr() != null) {
                book.setPublishDate(parseDate(book.getPublishDateStr()));
            }
        }
        System.out.println(list);
        booksService.saveBatch(list);

        System.out.println(content);

    }

    @Test
    void testParse(){
        System.out.println(parseDate("1643-9"));
        System.out.println(parseDate("2001 "));
    }

// 正确的日期解析方法
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return LocalDate.of(2000, 1, 1);
        }

        dateStr = dateStr.trim();

        try {
            // 处理 "2000-9" 格式（年份-月份）
            if (dateStr.matches("\\d{4}-\\d{1,2}")) {
                String[] parts = dateStr.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                return LocalDate.of(year, month, 1);
            }
            // 处理 "1996" 格式（只有年份）
            else if (dateStr.matches("\\d{4}")) {
                int year = Integer.parseInt(dateStr);
                return LocalDate.of(year, 1, 1);
            }
            // 处理完整日期格式 "2000-01-01"
            else if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(dateStr);
            }
        } catch (Exception e) {
            System.out.println("日期解析错误: " + dateStr + " - " + e.getMessage());
        }

        return LocalDate.of(2000, 1, 1);
    }
}
