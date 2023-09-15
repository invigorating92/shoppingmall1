package toyproject1.shopping.web.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject1.shopping.api.shop.domain.Item;
import toyproject1.shopping.api.shop.domain.ItemType;
import toyproject1.shopping.global.util.FileStore;
import toyproject1.shopping.global.util.UploadFile;
import toyproject1.shopping.api.shop.domain.Member;
import toyproject1.shopping.api.shop.repository.ItemRepository;
import toyproject1.shopping.api.shop.service.ItemService;
import toyproject1.shopping.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final FileStore fileStore;

    @GetMapping
    public String items(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        List<Item> items = itemRepository.findByMemberId2(loginMember.getMemberId());
        model.addAttribute("items", items);

        log.info("items 목록={}", items);
        return "items/items";
    }

    @GetMapping("/add")
    public String addItem(Model model){
        model.addAttribute("item",new ItemDTO());
        return "items/addForm";
    }

    @PostMapping("/add")
    public String postItem(@Validated @ModelAttribute("item") ItemDTO form, BindingResult bindingResult, HttpServletRequest request) throws IOException, NullPointerException {

        if (bindingResult.hasErrors()){
            return "items/addForm";
        }
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        /**
         * form으로 부터 multipart 받아와서 Item에 저장
         */

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> imageFiles = fileStore.storeFiles(form.getImageFiles());

//        if (attachFile == null){
//            bindingResult.reject("fileError");
//            return "items/addForm";
//        }

//        log.info("uploadFileName={}, storeFileName={}", attachFile.getUploadFileName(), attachFile.getStoreFileName());

        Item item = itemService.dtoToEntity(form, loginMember);
//        item.insertUploadFile(attachFile, imageFiles);
        itemService.save(item);

//        item.setAttachFile(attachFile);
//        item.setImageFiles(imageFiles);


//        Map<String, ItemSaveObject> itemStore = itemRepository.getItemStore();
//        ItemSaveObject find = itemStore.get(loginMember.getLoginId());
//
//        if (find == null){
//            ItemSaveObject newFind = new ItemSaveObject();
//            newFind.saveItem(item);
//            itemRepository.save(loginMember, newFind);
//            itemRepository.saveItemList(item);
//            log.info("postItem-newFind 문제");
//            return "redirect:/items";
//        }
//        log.info("postItem-find 문제");
//        find.saveItem(item);
//        itemRepository.save(loginMember, find);
//        itemRepository.saveItemList(item);
//        return "redirect:/items";

        return "redirect:/items";
    }

    @GetMapping("/{itemId}")
    public String myItemsPage(@PathVariable Long itemId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member sessionMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        Item findItem = itemRepository.findByItemId2(itemId);

        if (findItem == null){
            log.info("item 존재X");
            return "wrongAccessPage";
        }

        if (findItem.getMember().getMemberId() != sessionMember.getMemberId()){
            log.info("세션memberId, item의 매핑memberId 불일치");
            return "wrongAccessPage";
        }
        model.addAttribute("item", findItem);
        return "items/myItems";
    }

    @GetMapping("/modify/{itemId}")
    public String modifyItem(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findByItemId2(itemId);
        ItemDTO itemDTO = itemService.entityToDto(item);
        model.addAttribute("item", itemDTO);
        log.info("GetMapping modify item");
        return "items/modifyItem";
    }

    @PostMapping("/modify/{itemId}")
    public String modifyItemPost(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemDTO form, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute(SessionConst.MY_SESSION);

        if (bindingResult.hasErrors()){
            return "items/modifyItem";
        }

        Item item = itemService.dtoToEntity(form, loginMember);
        itemService.update(item);

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/items/{itemId}";
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values();
    }

    @GetMapping("/uploadEx")
    public String uploadEx(){

        return "items/uploadEx";
    }


}
