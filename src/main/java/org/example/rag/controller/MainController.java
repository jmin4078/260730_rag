package org.example.rag.controller;

import lombok.RequiredArgsConstructor;
import org.example.rag.service.DocumentService;
import org.example.rag.service.EmbeddingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MainController {
    private final EmbeddingService embeddingService;
    private final DocumentService documentService;

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping
    public String embed(
            @RequestParam String text,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("embed",
                Arrays.toString(embeddingService.embed(text))
        );
        return "redirect:/";
    }

    @PostMapping("/document")
    public String document(
            @RequestParam String content,
            @RequestParam String category) {
        documentService.save(content, category);
        return "redirect:/";
    }
    @PostMapping("/search")
    public String search(
            @RequestParam String query,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("search",
                documentService.search(query)
        );
        return "redirect:/";
    }
}