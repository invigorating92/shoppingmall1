package toyproject1.shopping.web.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UploadTestController {
    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadAjax(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            if (!uploadFile.getContentType().startsWith("static/images")) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }


            String originalFilename = uploadFile.getOriginalFilename();
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            log.info("fileName={}", fileName);

            //uuid
            String uuid = UUID.randomUUID().toString();

            //날짜 폴더 생성
            String datePath = makeFolder();

            //파일 이름
            String fullPath = fileDir + datePath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(fullPath);

            try {
                uploadFile.transferTo(savePath);
                resultDTOList.add(new UploadResultDTO(fileName, uuid, datePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

        private String makeFolder () {
            String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            String datePath = str.replace("/", File.separator);

            File uploadPathFolder = new File(fileDir, datePath);

            if (uploadPathFolder.exists() == false) {
                uploadPathFolder.mkdirs();
            }
            return datePath;
        }
    @GetMapping("/display")
    public ResponseEntity<byte[]> displayUpload(String fileName)  {
        ResponseEntity<byte[]> result = null;

        try{
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("img-srcFileName={}", srcFileName);

            File file = new File(fileDir + File.separator + srcFileName);
            log.info("file={}", file);

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);


        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
        }
    }

