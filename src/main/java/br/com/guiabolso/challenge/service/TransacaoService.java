package br.com.guiabolso.challenge.service;

import br.com.guiabolso.challenge.entity.Transacao;
import br.com.guiabolso.challenge.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransacaoService {

    public ResponseEntity<Response<List<Transacao>>> findByIdUsuarioAndAnoAndMes(int idUsuario, int ano, int mes) {

        List<Transacao> transacaoList = new ArrayList<Transacao>();
        Response<List<Transacao>> response = new Response<List<Transacao>>();

        try {

            /* validação dos parâmetros informados via URL */
            validateParams(idUsuario, ano, mes);

            /*
               - dado um conjunto de dados, deve ser retornada uma lista de transações
               - cada transação deve seguir o contrato de transação
               - a lista de transações deve ter um total de transações igual ao mês,
               multiplicado pelo primeiro dígito do id. Ex.: id 2995, mês 7, 2 * 7 = 14 transações na lista
               - dado dois conjuntos de dados iguais, as respostas devem ser as mesmas
               - isso significa que, para um mesmo id, mês e ano, deve ser retornada a mesma lista
            */
            int transacaoListTam = Integer.parseInt(String.valueOf(String.valueOf(idUsuario).charAt(0))) * mes;

            int temp = 0;
            while (transacaoListTam != temp) {
                Transacao transacao = new Transacao();

                /*
                    - cada transação deve ter uma data aleatória
                    - o campo data deve ter o formato timestamp
                    - o campo data deve ter o tipo long
                    - a data aleatória deve estar dentro do range de ano e mês dados
                 */
                transacao.setData(getRandomDate(ano, mes));

                /*
                    - cada transação deve ter descrição aleatória legível
                    - vocês devem criar a lógica para gerar essa descrição aleatória legível
                    - a descrição deve ter o tipo string
                    - essa descrição aleatória legível deve ser legível por humanos, isso significa que YhCekEr13RH não é válido, enquanto chaconapotalo pocanoçale é válido
                    - cada descrição deve ter no mínimo 10 caracteres
                    - cada descrição não pode superar 60 caracteres
                 */
                transacao.setDescricao(getHumanReadableString(10, 60));

                /*
                   - cada transação deve ter um valor baseado no id do usuário, no índice da transação e no mês
                   - o valor da transação deve ser representado por um número inteiro (reais sem centavos)
                   - o valor da transação deve estar entre -9.999.999 e 9.999.999, inclusive
                */
                transacao.setValor(ThreadLocalRandom.current().nextInt(-9999999, 9999999 + 1));

                transacaoList.add(transacao);
                temp++;
            }


        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(transacaoList);
        return ResponseEntity.ok(response);
    }

    /* gerador de descrição aleatória legível por humanos */
    private String getHumanReadableString(int minLength, int maxLength) {
        int readableStringLength = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);

        String vowels = "aeiou";
        String consoants = "bcçdfghjklmnpqrstvwxyz";

        char randomCharacter = 'a';
        int character = 0;

        int prefixSize = readableStringLength/2;
        if (maxLength%2 != 0) {
            prefixSize = ((int) (readableStringLength / 2)) + 1;
        }

        StringBuilder prefixPart = new StringBuilder(prefixSize);

        for (int i = 0; i < prefixSize-1; i++) {
            if (i%2 == 0) {
                character = (int)(Math.random()*consoants.length());
                randomCharacter = consoants.charAt(character);
                prefixPart.append(randomCharacter);
            } else {
                character = (int)(Math.random()*vowels.length());
                randomCharacter = vowels.charAt(character);
                prefixPart.append(randomCharacter);

                int spaceRandom = (int)(Math.random()*4);
                if (spaceRandom <= 1 && prefixPart.toString().length() >= 2)
                    prefixPart.append(" ");
            }
        }

        int suffixSize = readableStringLength - prefixSize;
        StringBuilder suffixPart = new StringBuilder(suffixSize);

        for (int i = 0; i < suffixSize-1; i++) {
            if (i%2 == 0) {
                character = (int)(Math.random()*consoants.length());
                randomCharacter = consoants.charAt(character);
                suffixPart.append(randomCharacter);
            } else {
                character = (int)(Math.random()*vowels.length());
                randomCharacter = vowels.charAt(character);
                suffixPart.append(randomCharacter);
            }
        }

        prefixPart.append(suffixPart);

        return prefixPart.toString();

    }

    /* gerador de data aleatória dentro do range de um mês e de um ano */
    private Long getRandomDate(int year, int month) {

        LocalDate ini = LocalDate.of(year, month, 1);
        int endOfMonth = ini.withDayOfMonth(ini.lengthOfMonth()).getDayOfMonth();
        LocalDate end = LocalDate.of(year, month, endOfMonth);

        Timestamp iniTimestamp = Timestamp.valueOf(ini.atTime(LocalTime.MIN));
        Timestamp endTimestamp = Timestamp.valueOf(end.atTime(LocalTime.MAX));

        return ThreadLocalRandom.current().nextLong(iniTimestamp.getTime(), endTimestamp.getTime());
    }

    private void validateParams(int userId, int year, int month) throws Exception {
        validateUserId(userId);
        validateYearMonth(year, month);
    }

    /* validação do ID do usuário */
    private void validateUserId(int userId) throws Exception {
        if (userId < 1000 || userId > 100000) {
            throw new Exception("O ID do usuário deve ser um número inteiro de 1.000 a 100.000");
        }
    }

    /* validação de mês e ano */
    public void validateYearMonth(int year, int month) throws Exception {

        if (!String.valueOf(year).matches("[0-9]{4}")) {
            throw new Exception("Formato do ano informado inválido. Favor informar os quatro dígitos do ano. Ex.: 2021");
        }

        if(month < 1 || month > 12) {
            throw new Exception("Mês informado inválido. Favor informar um valor de 1 a 12.");
        }

    }

}
