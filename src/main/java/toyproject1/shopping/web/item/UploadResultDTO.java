package toyproject1.shopping.web.item;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
    private String fileName;
    private String uuid;
    private String datePath;

    //전체 경로 조회 메서드
    public String getImageURL(){
        try{
            return URLEncoder.encode(datePath+"/"+uuid+"_"+fileName, "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

}
