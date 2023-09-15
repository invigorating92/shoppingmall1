package toyproject1.shopping.web.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import toyproject1.shopping.api.shop.domain.Item;
import toyproject1.shopping.api.shop.repository.ItemRepository;
import toyproject1.shopping.global.util.FileStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ShopController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @Transactional
    @GetMapping("/shop")
    public String shopHome(Model model){
//        List<Item> itemList = itemRepository.findAllItem();
        List<Item> itemList = itemRepository.findAll2();
        log.info("itemList={}", itemList.size());

        model.addAttribute("itemList", itemList);
        return "shop/shopHome";
    }

    @GetMapping("/shop/{id}")
    public String individualPage(@PathVariable Long id, HttpServletRequest request, Model model){

//        Map<Long, Item> itemList = itemRepository.getTotalItemStore();
        Item item = itemRepository.findByItemId2(id);

        model.addAttribute("item", item);
        return "shop/individualPage";
    }

    @GetMapping("/shop/basket/{memberId}")
    public String shopBasket(@PathVariable Long memberId, HttpServletRequest request, Model model){

        return "shop/shopBasket";
    }

//    @GetMapping("/images/{filename}")
//    public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws MalformedURLException {
//        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(filename));
//        return ResponseEntity.ok()
//                .body(urlResource);
//    }

//    @GetMapping("/attach/{itemId}")
//    public ResponseEntity<Resource> downloadAttachFile(@PathVariable Long itemId) throws MalformedURLException {
//        Map<Long, Item> itemList = itemRepository.getTotalItemStore();
//        Item item = itemList.get(itemId);
//
////        String uploadFileName = item.getAttachFile().getUploadFileName();
////        String storeFileName = item.getAttachFile().getStoreFileName();
//
//        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
//
//        log.info("UploadFileName={}", uploadFileName);
//        String encode = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\""+ encode +"\"";
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }

}
