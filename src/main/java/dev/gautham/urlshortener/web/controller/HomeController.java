package dev.gautham.urlshortener.web.controller;

import dev.gautham.urlshortener.domain.models.ShortUrlDto;
import dev.gautham.urlshortener.domain.service.ShortUrlService;
import dev.gautham.urlshortener.web.controller.dtos.CreateShortUrlForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ShortUrlService shorturlService;

    @GetMapping("/")
    public String home(Model model) {
        List<ShortUrlDto> shortUrls = shorturlService.findAllPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", "http://localhost:8080");
        model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
        return "index";
    }

    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm createShortUrlForm,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            List<ShortUrlDto> shortUrls = shorturlService.findAllPublicShortUrls();
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl", "http://localhost:8080");
            return "index";
        }

        redirectAttributes.addAttribute("successMessage", "Short URL created successfully.");
        return "redirect:/";
    }

}
