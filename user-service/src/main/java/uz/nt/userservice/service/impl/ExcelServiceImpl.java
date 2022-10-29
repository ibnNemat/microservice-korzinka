package uz.nt.userservice.service.impl;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import shared.libs.dto.UserDto;
import uz.nt.userservice.service.ExcelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void export(Stream<UserDto> userDtos, HttpServletRequest request, HttpServletResponse response) throws IOException {
        XSSFWorkbook xssfWorkbook =new XSSFWorkbook(new FileInputStream("user-service/src/main/resources/templates/UsersKorzinka.xlsx"));

        SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);


        Sheet sheet = workbook.getSheetAt(0);

        AtomicInteger r = new AtomicInteger(3);
        AtomicInteger i = new AtomicInteger(1);

        userDtos.forEach(userDto -> {
            Row row = sheet.createRow(r.getAndIncrement());
            row.createCell(0).setCellValue(i.getAndIncrement());
            row.createCell(1).setCellValue(userDto.getId());
            row.createCell(2).setCellValue(userDto.getFirstname());
            row.createCell(3).setCellValue(userDto.getLastname());
            row.createCell(4).setCellValue(userDto.getUsername());
            row.createCell(5).setCellValue(userDto.getEmail());
            row.createCell(6).setCellValue(userDto.getPhoneNumber());
            row.createCell(7).setCellValue(userDto.getCreated_at());

        });

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"users.xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
        response.getOutputStream().close();


    }
}
