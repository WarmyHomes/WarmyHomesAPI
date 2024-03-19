package com.project.service.helper;

import com.project.entity.business.Category;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.messages.SuccessMessages;
import com.project.repository.business.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryRepository categoryRepository;






    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
    private static final Pattern EDGESDASHES = Pattern.compile("(^-|-$)");

    public String toSlug(String input, Long id) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDASHES.matcher(slug).replaceAll("");
        slug = slug.toLowerCase(Locale.ENGLISH);

        if (id != null) {
            slug += "-" + id;
        }

        return slug;
    }

    public String formatAsWordsCase(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Anahtar boş olamaz.");
        }

        StringBuilder formattedKeyBuilder = new StringBuilder(key.length());
        boolean capitalizeNext = true;

        for (char ch : key.toCharArray()) {
            if (capitalizeNext && Character.isLetter(ch)) {
                ch = Character.toUpperCase(ch);
                capitalizeNext = false;
            } else if (ch == ' ') { // Burada sadece boşluk karakterini kontrol ediyorum, gerekiyorsa diğer ayıraçlar da eklenebilir.
                capitalizeNext = true;
            } else {
                ch = Character.toLowerCase(ch); // Büyük harf dışındaki karakterleri küçük harfe çevir.
            }

            formattedKeyBuilder.append(ch);
        }

        return formattedKeyBuilder.toString();
    }



    // Kategori başlığının benzersiz olup olmadığını kontrol eden ve değilse custom exception fırlatan metot
    public void isCategoryTitleUnique(String title, Long id) {
        boolean isUnique = categoryRepository.findByTitle(title)
                .map(category -> category.getId().equals(id)) // Güncellenen kategori mi?
                .orElse(true); // Eğer kategori bulunamazsa, başlık benzersizdir

        if (!isUnique) {
            //todo mesaji duzelt
            throw new ConflictException(SuccessMessages.CATEGORY_CONFLIG + title);
        }
    }

    public void validateCategorySlugUniqueness(String slug, Long idToUpdate) {
        Optional<Category> categoryWithSlug = categoryRepository.findBySlug(slug);

        // Eğer bir kategori bulunursa, ID'lerin eşleşip eşleşmediğine bakıyoruz.
        if (categoryWithSlug.isPresent()) {
            // Güncelleme yapılıyorsa ve bulunan kategorinin ID'si güncellenen kategoriye ait değilse, çakışma var demektir.
            if (idToUpdate != null && !categoryWithSlug.get().getId().equals(idToUpdate)) {
                throw new ConflictException("Slug '" + slug + "' is already in use by another category.");
            }
            // Yeni bir kategori oluşturuluyorsa ve bir kategori zaten bu slug'ı kullanıyorsa, çakışma var demektir.
            else if (idToUpdate == null) {
                throw new ConflictException("Slug '" + slug + "' is already in use by another category.");
            }
        }
        // Eğer kategori bulunamazsa veya mevcut kategori güncellenen kategori ise, slug benzersizdir.
    }

    // Kategori slug'ının benzersiz olup olmadığını kontrol eden ve değilse custom exception fırlatan metot


    public Category findCategoryById(Long id) {

        return   categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Category daha once olusturulmus", "id", id));


    }

}
