package dev.gautham.urlshortener.web.controller;

import dev.gautham.urlshortener.ApplicationProperties;
import dev.gautham.urlshortener.domain.exception.ShortUrlNotFoundException;
import dev.gautham.urlshortener.domain.models.CreateShortUrlCmd;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ShortUrlService shorturlService;
    private final ApplicationProperties properties;

    @GetMapping("/")
    public String home(Model model) {
        List<ShortUrlDto> shortUrls = shorturlService.findAllPublicShortUrls();
        model.addAttribute("shortUrls", shortUrls);
        model.addAttribute("baseUrl", properties.baseUrl());
        model.addAttribute("createShortUrlForm", new CreateShortUrlForm(""));
        return "index";
    }

    @PostMapping("/short-urls")
    public String createShortUrl(@ModelAttribute("createShortUrlForm") @Valid CreateShortUrlForm form,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            List<ShortUrlDto> shortUrls = shorturlService.findAllPublicShortUrls();
            model.addAttribute("shortUrls", shortUrls);
            model.addAttribute("baseUrl", properties.baseUrl());
            return "index";
        }
        try {
            CreateShortUrlCmd cmd = new CreateShortUrlCmd(form.originalUrl());
            var shortUrlDto = shorturlService.createShortUrl(cmd);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Short URL created successfully " + properties.baseUrl()+ "/s/" + shortUrlDto.shortKey());
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create short URL.");
        }
        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    String redirectShortUrl(@PathVariable String shortKey) {
        ShortUrlDto shortUrlDto = shorturlService.accessShortUrl(shortKey)
                .orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found" + shortKey));
        return "redirect:" + shortUrlDto.originalUrl();
    }

}
