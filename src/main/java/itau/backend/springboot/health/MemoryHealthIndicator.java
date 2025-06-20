package itau.backend.springboot.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("jvmMemory")
public class MemoryHealthIndicator implements HealthIndicator {
    // Define um percentual mínimo de memória heap livre.
    // Se a memória livre cair abaixo de 10% da memória máxima, o status será DOWN.
    private static final double MINIMO_MEMORIA_LIVRE = 0.10; // 10%

    @Override
    public Health health() {
        Runtime runtime = Runtime.getRuntime();

        // Memória heap total da JVM
        long maxMemoria = runtime.maxMemory();
        // Memória heap atualmente alocada para a JVM
        long memoriaTotal = runtime.totalMemory();
        // Memória heap atualmente livre
        long memoriaLivre = runtime.freeMemory();
        // Memória heap usada
        long memoriaEmUso = memoriaTotal - memoriaLivre;
        // Memória heap livre em relação ao máximo que pode ser alocado
        long relacaoMemoriaLivreMemoriaEmUso = maxMemoria - memoriaEmUso;

        // Calcula o percentual de memória livre em relação à memória máxima
        double porcentagemLivre = (double) relacaoMemoriaLivreMemoriaEmUso / maxMemoria;

        if (porcentagemLivre < MINIMO_MEMORIA_LIVRE) {
            // Se a memória livre estiver abaixo do limiar, a aplicação está DOWN
            return Health.down()
                    .withDetail("resposta", "A memória da JVM está criticamente baixa.")
                    .withDetail("maxMemoria", formatBytes(maxMemoria))
                    .withDetail("memoriaTotal", formatBytes(memoriaTotal))
                    .withDetail("memoriaEmUso", formatBytes(memoriaEmUso))
                    .withDetail("memoriaLivre", formatBytes(memoriaLivre))
                    .withDetail("relacaoMemoriaLivreMemoriaEmUso", formatBytes(relacaoMemoriaLivreMemoriaEmUso))
                    .withDetail("porcentagemLivre", String.format("%.2f%%", porcentagemLivre * 100))
                    .withDetail("porcentagemLimite", String.format("%.2f%%", MINIMO_MEMORIA_LIVRE * 100))
                    .build();
        }

        // Caso contrário, a aplicação está UP
        return Health.up()
                .withDetail("resposta", "O uso de memória da JVM está saudável.")
                .withDetail("maxMemoria", formatBytes(maxMemoria))
                .withDetail("memoriaTotal", formatBytes(memoriaTotal))
                .withDetail("memoriaEmUso", formatBytes(memoriaEmUso))
                .withDetail("memoriaLivre", formatBytes(memoriaLivre))
                .withDetail("relacaoMemoriaLivreMemoriaEmUso", formatBytes(relacaoMemoriaLivreMemoriaEmUso))
                .withDetail("porcentagemLivre", String.format("%.2f%%", porcentagemLivre * 100))
                .build();
    }

    // Método auxiliar para formatar bytes em uma unidade mais legível (KB, MB, GB)
    private String formatBytes(long bytes) {
        long kilobytes = bytes / 1024;
        long megabytes = kilobytes / 1024;
        long gigabytes = megabytes / 1024;

        if (gigabytes > 0) {
            return String.format("%.2f GB", (double)bytes / (1024 * 1024 * 1024));
        } else if (megabytes > 0) {
            return String.format("%.2f MB", (double)bytes / (1024 * 1024));
        } else {
            return String.format("%d KB", kilobytes);
        }
    }
}
