package com.example.imoveistemporada;

/**
 * Representa os dados meteorológicos obtidos da API OpenWeatherMap.
 */
public class DadosClima {
    // Representa as informações principais do clima, como temperatura e umidade.
    private Main main;

    // Representa as informações sobre o clima, como a descrição das condições meteorológicas.
    private Weather[] weather;

    /**
     * Classe interna que representa as informações principais do clima.
     */
    public static class Main {
        private double temp;     // Temperatura em graus Celsius.
        private double humidity; // Umidade em percentagem.

        /**
         * Obtém a temperatura.
         *
         * @return A temperatura em graus Celsius.
         */
        public double getTemp() {
            return temp;
        }

        /**
         * Obtém a umidade.
         *
         * @return A umidade em percentagem.
         */
        public double getHumidity() {
            return humidity;
        }
    }

    /**
     * Classe interna que representa as informações sobre o clima.
     */
    public static class Weather {
        private String description; // Descrição das condições meteorológicas.

        /**
         * Obtém a descrição das condições meteorológicas.
         *
         * @return A descrição do clima.
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Obtém as informações principais do clima.
     *
     * @return As informações principais do clima.
     */
    public Main getMain() {
        return main;
    }

    /**
     * Obtém as informações sobre o clima.
     *
     * @return As informações sobre o clima.
     */
    public Weather[] getWeather() {
        return weather;
    }
}
