package br.com.codenation;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.desafio.models.Jogador;
import br.com.codenation.desafio.models.Time;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

    List<Time> times;

    public DesafioMeuTimeApplication() {
        times = new ArrayList<Time>();
    }

    @Desafio("incluirTime")
    public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        if (existeTime(id)) {
            throw new IdentificadorUtilizadoException();
        } else {
            Time time = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
            times.add(time);
        }
    }

    @Desafio("incluirJogador")
    public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                Jogador jogador = new Jogador(id, nome, dataNascimento, nivelHabilidade, salario);
                if (existeJogador(id, time.getJogadores())) {
                    throw new IdentificadorUtilizadoException();
                } else {
                    time.getJogadores().add(jogador);
                    return;
                }
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("definirCapitao")
    public void definirCapitao(Long idJogador) {
        for (Time time : times) {
            for (Jogador jogador : time.getJogadores()) {
                if (jogador.getId().equals(idJogador)) {
                    time.setIdCapitao(idJogador);
                    return;
                }
            }
        }
        throw new JogadorNaoEncontradoException();
    }

    @Desafio("buscarCapitaoDoTime")
    public Long buscarCapitaoDoTime(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                if (time.getIdCapitao() == null) {
                    throw new CapitaoNaoInformadoException();
                } else {
                    return time.getIdCapitao();
                }
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarNomeJogador")
    public String buscarNomeJogador(Long idJogador) {
        for (Time time : times) {
            for (Jogador jogador : time.getJogadores()) {
                if (jogador.getId().equals(idJogador)) {
                    return jogador.getNome();
                }
            }
        }
        throw new JogadorNaoEncontradoException();
    }

    @Desafio("buscarNomeTime")
    public String buscarNomeTime(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                return time.getNome();
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarJogadoresDoTime")
    public List<Long> buscarJogadoresDoTime(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                List<Long> idJogadores = new ArrayList<Long>();
                time.getJogadores().forEach(jogador -> idJogadores.add(jogador.getId()));
                return idJogadores;
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarMelhorJogadorDoTime")
    public Long buscarMelhorJogadorDoTime(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                Jogador jogadorMaisHabilidoso = time.getJogadores().stream().
                        max(Comparator.comparing(Jogador::getNivelHabilidade)).
                        orElseThrow(JogadorNaoEncontradoException::new);
                return jogadorMaisHabilidoso.getId();
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarJogadorMaisVelho")
    public Long buscarJogadorMaisVelho(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                Jogador jogadorMaisVelho = time.getJogadores().stream().
                        min(Comparator.comparing(Jogador::getDataNascimento)).
                        orElseThrow(JogadorNaoEncontradoException::new);
                return jogadorMaisVelho.getId();
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarTimes")
    public List<Long> buscarTimes() {
        List<Long> idTimes = new ArrayList<Long>();
        times.forEach(time -> idTimes.add(time.getId()));
        return idTimes;
    }

    @Desafio("buscarJogadorMaiorSalario")
    public Long buscarJogadorMaiorSalario(Long idTime) {
        for (Time time : times) {
            if (time.getId().equals(idTime)) {
                Jogador jogadorMaiorSalario = time.getJogadores().stream().
                        max(Comparator.comparing(Jogador::getSalario)).
                        orElseThrow(JogadorNaoEncontradoException::new);
                return jogadorMaiorSalario.getId();
            }
        }
        throw new TimeNaoEncontradoException();
    }

    @Desafio("buscarSalarioDoJogador")
    public BigDecimal buscarSalarioDoJogador(Long idJogador) {
        for (Time time : times) {
            for (Jogador jogador : time.getJogadores()) {
                if (jogador.getId().equals(idJogador)) {
                    return jogador.getSalario();
                }
            }
        }
        throw new JogadorNaoEncontradoException();
    }

    @Desafio("buscarTopJogadores")
    public List<Long> buscarTopJogadores(Integer top) {
        List<Jogador> jogadores = new ArrayList<>();
        for (Time time : times) {
            jogadores.addAll(time.getJogadores());
        }
        List<Long> topIdJogadores = jogadores.stream()
                .sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed().
                        thenComparing(Jogador::getId)).limit(top).map(Jogador::getId).collect(Collectors.toList());
        return topIdJogadores;
    }

    @Desafio("buscarCorCamisaTimeDeFora")
    public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
        Time timeCasa = null;
        Time timeFora = null;
        for (Time time : times) {
            if (time.getId().equals(timeDaCasa)) {
                timeCasa = time;
            }
            if (time.getId().equals(timeDeFora)) {
                timeFora = time;
            }
        }
        return defineCorCamisaTimeDeFora(timeCasa, timeFora);
    }

    private String defineCorCamisaTimeDeFora(Time timeDaCasa, Time timeDeFora) {
        if (timeDaCasa == null || timeDeFora == null) {
            throw new TimeNaoEncontradoException();
        }
        if (timeDaCasa.getCorUniformePrincipal().equals(timeDeFora.getCorUniformePrincipal())) {
            return timeDeFora.getCorUniformeSecundario();
        } else {
            return timeDeFora.getCorUniformePrincipal();
        }
    }

    private boolean existeTime(Long id) {
        return times.stream().anyMatch(t -> t.getId().equals(id));
    }

    private boolean existeJogador(Long id, List<Jogador> jogadores) {
        return jogadores.stream().anyMatch(j -> j.getId().equals(id));
    }


}
