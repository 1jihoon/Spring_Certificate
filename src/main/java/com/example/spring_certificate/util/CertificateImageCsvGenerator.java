package com.example.spring_certificate.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CertificateImageCsvGenerator {

    private static final String BASE_PATH ="src/main/resources/static/img/Book";
    private static final String OUTPUT_PATH = "src/main/resources/csv/certificate_image.csv";

    private static final Map<String, String> nameMap = new HashMap<>();

    static {
        nameMap.put("mechanic", "일반기계기사");
        nameMap.put("engineer_construction", "건설기계설비기사");
        nameMap.put("mechanical_fire", "소방설비기사(기계)");
        nameMap.put("mechanical_electric", "소방설비기사(전기)");
        nameMap.put("engineer_hazardous", "위험물산업기사");
        nameMap.put("electric_engineer", "전기기사");
        nameMap.put("electric_engineering", "전기산업기사");
        nameMap.put("electrical_work_engineer", "전기공사기사");
        nameMap.put("electronic_engineer", "전자기사");
        nameMap.put("radio_engineer", "무선설비기사");
        nameMap.put("information_engineer", "정보통신기사");
        nameMap.put("information_processing_engineer", "정보처리기사");
        nameMap.put("graphic_design", "GTQ");
        nameMap.put("game_planner", "게임기획전문가");
        nameMap.put("game_graphic_designer", "게임그래픽전문가");
        nameMap.put("big_data_analyst", "빅데이터분석기사");
        nameMap.put("ai_industry", "인공지능산업기사");
        nameMap.put("industrial_engineer_product_design","제품디자인산업기사");
        nameMap.put("visual_communication_design", "시각디자인산업기사");
        nameMap.put("computer_graphic","컴퓨터그래픽기능사");
        nameMap.put("fashion_design", "패션디자인산업기사");
        nameMap.put("cloth_design", "의류기사");
        nameMap.put("interior_ architecture", "실내건축산업기사");
        nameMap.put("multimedia_content", "멀티미디어콘텐츠제작전문가");
        nameMap.put("advertisement_engineer", "옥외광고사");
        nameMap.put("video_editing", "영상편집전문가");
        nameMap.put("ACP", "ACP");
        nameMap.put("culture_art", "문화예술교육사");
        nameMap.put("nutritionist", "영양사");
        nameMap.put("hygienist", "위생사");
        nameMap.put("medical_information","의료정보관리사");
        nameMap.put("hospital_ administrator", "병원행정사");
        nameMap.put("occupational_therapist", "작업치료사");
        nameMap.put("animal_nurse","동물간호복지사");
        nameMap.put("emergency", "응급구조사");
        nameMap.put("chemical_analysis_technician", "화학분석기능사");
        nameMap.put("biochemical_manufacturing", "바이오화학제품제조기사");
        nameMap.put("chemical_engineer", "화공기사");
        nameMap.put("makeup_ beautician", "미용사(메이크업)");
        nameMap.put("cosmetic_ manufacturing_manager", "화장품제조관리사");
        nameMap.put("social_worker", "사회복지사 1급");
        nameMap.put("rehabilitation_ specialist", "재활운동전문가");
        nameMap.put("pets", "반려동물종합관리사");
        nameMap.put("western_food", "양식조리기능사");
        nameMap.put("korean_food", "한식조리기능사");
        nameMap.put("barista", "바리스타(1급)");
        nameMap.put("bread", "제과기능사");
        nameMap.put("tour_guide", "관광통역안내사");
        nameMap.put("hotel_service","호텔서비스사");
        nameMap.put("JLPT", "JLPT");
        nameMap.put("ERP_information", "ERP정보관리사");
        nameMap.put("computerized_accounting", "전산회계 1급");
        nameMap.put("CS_Leaders", "CS Leaders(서비스경영자격)");
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));
        writer.write("certificateName,url,altText\n");

        Path absolutePath = Paths.get("").toAbsolutePath().resolve(BASE_PATH);
        Files.list(absolutePath).filter(Files::isDirectory).forEach(folder -> {
            String folderName = folder.getFileName().toString();
            String certificateName = nameMap.getOrDefault(folderName, folderName);

            try {
                Files.list(folder)
                        .filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                        .forEach(img -> {
                            String url = "/img/Book/" + folderName + "/" + img.getFileName();
                            try {
                                writer.write(String.format("%s,%s,대표 이미지\n", certificateName, url));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
        System.out.println("✅ certificate_image.csv 생성 완료!");
    }

}
