package com.kh.app3.web;

import com.kh.app3.domain.notice.Notice;
import com.kh.app3.domain.notice.svc.NoticeSVC;
import com.kh.app3.web.form.notice.AddForm;
import com.kh.app3.web.form.notice.DetailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

  private final NoticeSVC noticeSVC;

  //  등록화면
  @GetMapping("/add")
  public String addForm(@ModelAttribute AddForm addFrom) {
    log.info("NoticeController.addForm() 호출됨!");
    return "notice/addForm";
  }

//  public String addForm(Model model) {
//    log.info("NoticeController.addForm() 호출됨!");
//    model.addAttribute("addForm",new AddForm());
//    return "notice/addForm";
//  }
  //  등록처리
  @PostMapping("/add")
  public String add(
      @ModelAttribute AddForm addForm,
      RedirectAttributes redirectAttributes){

    log.info("NoticeController.add() 호출됨!");
    log.info("Adform={}",addForm);

    Notice notice = new Notice();
    notice.setSubject(addForm.getSubject());
    notice.setContent(addForm.getContent());
    notice.setAuthor(addForm.getAuthor());

    Notice writedNotice = noticeSVC.write(notice);
    redirectAttributes.addAttribute("noticeId",writedNotice.getNoticeId());

    return "redirect:/notices/{noticeId}";  //http://서버:9080/notices/공지사항번호
  }
  //  상세화면
  @GetMapping("/{noticeId}")
  public String detailForm(@PathVariable Long noticeId, Model model){

    Notice notice = noticeSVC.findByNoticeId(noticeId);

    DetailForm detailForm = new DetailForm();
    detailForm.setSubject(notice.getSubject());
    detailForm.setContent(notice.getContent());
    detailForm.setAuthor(notice.getAuthor());

    model.addAttribute("detailForm",detailForm);

    return "notice/detailForm";
  }
  //  수정화면
  @GetMapping("/{noticeId}/edit")
  public String editForm(){
    return "notice/editForm";
  }
  //  수정처리
  @PostMapping("/{noticeId}/edit")
  public String edit(){

    return "redirect:/notices/{noticeId}";
  }
  //  삭제처리
  @GetMapping("{noticeId}/del")
  public String del(){

    return "redirect:/notices";
  }
  //  전체목록
  @GetMapping("")
  public String list(){

    return "notice/list";
  }

}
