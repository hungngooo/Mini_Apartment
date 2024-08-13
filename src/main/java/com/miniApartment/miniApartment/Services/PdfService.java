package com.miniApartment.miniApartment.Services;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.ContractDetail;
import com.miniApartment.miniApartment.Entity.Tenants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private TenantService tenantService;

    public byte[] generatePdf(Contract contract, List<Tenants> tenants, ContractDetail contractDetail) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Load and set font that supports Vietnamese with UTF-8 encoding
            String fontPath = "src/main/resources/fonts/times.ttf"; // Path to your font file
            PdfFont font = PdfFontFactory.createFont(fontPath, "Identity-H", true);

            // Set font for the entire document
            document.setFont(font);

            Date date = new Date();
            String dateFormat = sdf.format(date);
            document.add(new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM"));
            document.add(new Paragraph("Độc lập - Tự do - Hạnh phúc"));
            document.add(new Paragraph("**********"));
            document.add(new Paragraph("HỢP ĐỒNG THUÊ CĂN HỘ CHUNG CƯ MINI"));
            document.add(new Paragraph("(Số: ........../HĐTCHCCM)"));

            document.add(new Paragraph("- Căn cứ Luật dân sự số 91/2015/QH13 và Luật nhà ở số 65/2014/QH13 của nước Cộng hòa Xã hội Chủ nghĩa Việt Nam ban hành năm 2015;"));
            document.add(new Paragraph("- Căn cứ vào pháp luật khác có liên quan;"));
            document.add(new Paragraph("- Căn cứ vào nhu cầu và khả năng của các bên;"));
            document.add(new Paragraph("- Hôm nay," + dateFormat + " Tại địa chỉ số 1 đường Phú Mỹ, Mỹ Đình, Nam Từ Liêm, Hà Nội, chúng tôi gồm có:"));

            // Ví dụ cho phần thông tin bên thuê và bên cho thuê
            document.add(new Paragraph("ĐẠI DIỆN BÊN CHO THUÊ (BÊN A):"));
            document.add(new Paragraph("Ông/bà: Nguyễn Văn D Sinh ngày: 01/01/198x"));
            document.add(new Paragraph("CMND/CCCD số: 02209800xxxx Ngày cấp: 01/01/2022 Nơi cấp: Cục cảnh sát"));
            document.add(new Paragraph("Hộ khẩu: số 1 đường Trần Bình, Mỹ Đình, Nam Từ Liêm, Hà Nội"));
            document.add(new Paragraph("Địa chỉ: số 1 đường Phú Mỹ, Mỹ Đình, Nam Từ Liêm, Hà Nội"));
            document.add(new Paragraph("Điện thoại: 0123 456 899"));
            document.add(new Paragraph("Là chủ sở hữu nhà ở: số 1 đường Phú Mỹ, Mỹ Đình, Nam Từ Liêm, Hà Nội"));

            // Thông tin bên thuê
            for (Tenants tenant : tenants) {

                String citizenDate = sdf.format(tenant.getCreateCitizenIdDate());
                document.add(new Paragraph("ĐẠI DIỆN BÊN THUÊ (BÊN B):"));
                document.add(new Paragraph("Ông/bà: " + contract.getRepresentative()));
                document.add(new Paragraph("CMND/CCCD: " + tenant.getCitizenId() + " Ngày cấp: " + citizenDate + " Nơi cấp: " + tenant.getCreateCitizenIdPlace()));
                document.add(new Paragraph("Hộ khẩu: " + tenant.getPlaceOfPermanet()));
                document.add(new Paragraph("Số điện thoại: " + tenant.getContact() + " Email " + tenant.getEmail()));
                document.add(new Paragraph("Hai bên cùng thỏa thuận ký hợp đồng với những nội dung sau:"));
                document.add(new Paragraph("ĐIỀU 1: ĐỐI TƯỢNG VÀ NỘI DUNG CỦA HỢP ĐỒNG"));
                document.add(new Paragraph("1.1. Bên A cho bên B thuê căn hộ số: " + tenant.getRoomId()));
                document.add(new Paragraph("Tại: số 1 đường Phú Mỹ, Mỹ Đình, Nam Từ Liêm, Hà Nội"));
                document.add(new Paragraph("Để sử dụng vào mục đích: để ở (không trái với quy định pháp luật)"));
                document.add(new Paragraph("1.2. Quyền sở hữu của bên A đối với căn hộ cụ thể như sau:"));
                document.add(new Paragraph("a) Địa chỉ căn hộ: số 1 đường Phú Mỹ, Mỹ Đình, Nam Từ Liêm, Hà Nội"));
                document.add(new Paragraph("b) Căn hộ số: " + tenant.getRoomId()));
                document.add(new Paragraph("d) Tổng diện tích sàn căn hộ là: " + contractDetail.getTotalArea() + " m2; diện tích đất gắn liền với căn hộ là:" + contractDetail.getLandArea()));
                document.add(new Paragraph("(sử dụng chung là: " + contractDetail.getPublicArea() + " m2; sử dụng riêng là: " + contractDetail.getPrivateArea() + " m2)."));
                document.add(new Paragraph("e) Trang thiết bị gắn liền với căn hộ: " + contractDetail.getDevice()));
                document.add(new Paragraph("f) Nguồn gốc sở hữu: " + contractDetail.getOwnerOrigin()));
                document.add(new Paragraph("g) Những hạn chế về quyền sở hữu căn hộ (nếu có): " + contractDetail.getOwnerLimit()));
                document.add(new Paragraph("1.3. Thông tin người thuê (bao gồm người đại diện và người ở cùng): "));
                document.add(new Paragraph("a) Số lượng khách thuê tối đa: " + contract.getNumberOfTenant() + " người"));
                document.add(new Paragraph("b) Dưới đây là thông tin chi tiết của khách thuê:"));
            }
            Table table = new Table(12); // Số cột = 12

            // Thêm tiêu đề cho bảng
            table.addCell("No.");
            table.addCell("Full Name");
            table.addCell("Gender");
            table.addCell("D.O.B");
            table.addCell("Mobile No");
            table.addCell("Email ID");
            table.addCell("Citizen ID");
            table.addCell("Career");
            table.addCell("Licence plate");
            table.addCell("Vehicle Type");
            table.addCell("Vehicle color");
            table.addCell("Relationship with Representative");

            int index = 1; // Bắt đầu từ 1 thay vì 0

            // Thêm dữ liệu vào bảng
            for (Tenants tenant : tenants) {
                String formattedTenantDate = sdf.format(tenant.getDateOfBirth());
                table.addCell(String.valueOf(index)); // Tăng số tự động
                table.addCell(tenant.getFirstName() + " " + tenant.getLastName());
                table.addCell(String.valueOf(tenant.getGender()));
                table.addCell(formattedTenantDate); // Chuyển ngày sinh thành chuỗi
                table.addCell(tenant.getContact());
                table.addCell(tenant.getEmail());
                table.addCell(tenant.getCitizenId());
                table.addCell(tenant.getCareer());
                table.addCell(tenant.getLicensePlate());
                table.addCell(tenant.getVehicleType());
                table.addCell(tenant.getVehicleColor());
                table.addCell(tenant.getRelationship());

                index++; // Tăng biến đếm lên 1 sau mỗi tenant
            }

            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }
}
