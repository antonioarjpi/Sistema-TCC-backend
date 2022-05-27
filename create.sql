create table acompanhamento_orientacao (
id int8 generated by default as identity
, data_mudanca timestamp, status_orientacao varchar(255), devolutiva_id int8, orientacao_id int8, primary key (id));
create table aluno (id int8 generated by default as identity,
email varchar(255), matricula varchar(255), nome varchar(255), senha varchar(255), equipe_id int8, primary key (id));
create table area_conhecimento (id int8 generated by default as identity, descricao varchar(255), primary key (id));
create table banca (id int8 generated by default as identity, data_banca timestamp, descricao varchar(255), ordem_apresentacao int4, equipe_id int8, membro_banca_id int8, orientador_id int8, primary key (id));
create table defesa (id int8 generated by default as identity, data_defesa timestamp, banca_id int8, primary key (id));
create table devolutiva (id int8 generated by default as identity, descricao varchar(255), versao_doc varchar(255), local_correcao_id int8, orientacao_id int8, primary key (id));
create table equipe (id int8 generated by default as identity, data_cadastro timestamp, nome varchar(255), quantidade int4, tema_id int8, primary key (id));
create table estrutura_tcc (id int8 generated by default as identity, descricao varchar(255), tipo_tcc_id int8, primary key (id));
create table linha_pesquisa (id int8 generated by default as identity, descricao varchar(255), area_conhecimento_id int8, primary key (id));
create table local_correcao (id int8 generated by default as identity, correcao_sugerida varchar(255), local varchar(255), primary key (id));
create table membro_banca (id int8 generated by default as identity, matricula varchar(255), primary key (id));
create table orientacao (id int8 generated by default as identity, data_orientacao timestamp, estrutura_tcc_id int8, orientador_id int8, primary key (id));
create table orientador (id int8 generated by default as identity, email varchar(255), matricula varchar(255), nome varchar(255), senha varchar(255), titulacao_id int8, primary key (id));
create table tema (id int8 generated by default as identity, delimitacao varchar(255), linha_pesquisa_id int8, primary key (id));
create table tipo_tcc (id int8 generated by default as identity, descricao varchar(255), primary key (id));
create table titulacao (id int8 generated by default as identity, descricao varchar(255), grau varchar(255), ies varchar(255), primary key (id));
alter table if exists acompanhamento_orientacao add constraint FKbvwoohkt1r4xgx8q7qc76dlkc foreign key (devolutiva_id) references devolutiva;
alter table if exists acompanhamento_orientacao add constraint FK3gahxyb4h7k7fjwp25cwddla foreign key (orientacao_id) references orientacao;
alter table if exists aluno add constraint FK7dlv2upxidsj0tnqje2vesdt6 foreign key (equipe_id) references equipe;
alter table if exists banca add constraint FKpwf1q0ku41m17lw7qfx102ysr foreign key (equipe_id) references equipe;
alter table if exists banca add constraint FK93w3341dkvbcay8v3n0vyxsyi foreign key (membro_banca_id) references membro_banca;
alter table if exists banca add constraint FK8jp8bjrjoxyfvlgcuwp2kuahv foreign key (orientador_id) references orientador;
alter table if exists defesa add constraint FKl8ufb16neijpom3ilg2nf3g2r foreign key (banca_id) references banca;
alter table if exists devolutiva add constraint FK3jferdo2lxysl1eya4a7rrcl1 foreign key (local_correcao_id) references local_correcao;
alter table if exists devolutiva add constraint FKgsyugcpp8om9p4xp9a66fjrkw foreign key (orientacao_id) references orientacao;
alter table if exists equipe add constraint FKo8agxldrtjrfqudokvd9bxn49 foreign key (tema_id) references tema;
alter table if exists estrutura_tcc add constraint FK8yo2t0r00u32q5gm5lrrykyei foreign key (tipo_tcc_id) references tipo_tcc;
alter table if exists linha_pesquisa add constraint FKc8rth7frmtxvtgttp798c9aex foreign key (area_conhecimento_id) references area_conhecimento;
alter table if exists orientacao add constraint FKqiiqgov27xeol0udw3pr0fj32 foreign key (estrutura_tcc_id) references estrutura_tcc;
alter table if exists orientacao add constraint FKcf7ngkwbrmipo3i0mic8pv54j foreign key (orientador_id) references orientador;
alter table if exists orientador add constraint FKa9yg7r29i0aufulq5tfxdp18a foreign key (titulacao_id) references titulacao;
alter table if exists tema add constraint FKdwbai90aitqghq43u16qa9rfu foreign key (linha_pesquisa_id) references linha_pesquisa;
