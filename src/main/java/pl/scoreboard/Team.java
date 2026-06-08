package pl.scoreboard;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Team(String name) {
    public Team {
        name = normalize(name);
    }

    private static String normalize(String rawName) {
        if (rawName == null || rawName.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null or blank");
        }

        return Arrays.stream(rawName.trim().split("\\s+"))
                .map(word -> {
                    String lower = word.toLowerCase();
                    return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
                })
                .collect(Collectors.joining(" "));
    }
}