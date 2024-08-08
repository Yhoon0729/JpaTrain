package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUpPage() {
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm) {
        // System.out.println(memberForm.toString());
        log.info(memberForm.toString());

        Member member = memberForm.toEntity();
        // System.out.println(member.toString());
        log.info(member.toString());

        Member saved = memberRepository.save(member);
        // System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/members";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 1. id 조회해서 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String showAll(Model model) {
        // 1. 모든 데이터 가져오기
        List<Member> memberEntityList = memberRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("memberList", memberEntityList);

        // 3. 뷰 페이지 설정하기
        return "members/showAll";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 수정할 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 2. 데이터 모델에 등록
        model.addAttribute("member", memberEntity);

        // 3. 뷰 페이지 설정하기
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());

        // 2. repository 이용하여 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        log.info(target.toString());
        // 2-2. 기존 데이터 값을 갱신하기
        if(target != null) {
            memberRepository.save(memberEntity);
        }

        // 3. 수정 결과 페이지 리다이렉트
        return "redirect:/members/"+memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        // 1. 삭제할 엔티티 가져오기
        Member target = memberRepository.findById(id).orElse(null);

        // 2. 대상 엔티티 삭제하기
        if(target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", target.getEmail() + "회원이 삭제되었습니다.");
        }

        // 3. 페이지 redirect
        return "redirect:/members";
    }
}
