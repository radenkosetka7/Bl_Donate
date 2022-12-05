-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema bl_donate
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bl_donate
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bl_donate` DEFAULT CHARACTER SET utf8 ;
USE `bl_donate` ;

-- -----------------------------------------------------
-- Table `bl_donate`.`korisnik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`korisnik` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `korisnicko_ime` VARCHAR(45) NOT NULL,
  `lozinka` MEDIUMTEXT NOT NULL,
  `ime` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `broj_telefona` CHAR(11) NOT NULL,
  `adresa` VARCHAR(45) NULL DEFAULT NULL,
  `prezime` VARCHAR(45) NULL DEFAULT NULL,
  `logo` LONGBLOB NULL DEFAULT NULL,
  `jmbg` CHAR(13) NULL DEFAULT NULL,
  `pib` CHAR(13) NULL DEFAULT NULL,
  `rola` TINYINT NOT NULL,
  `status` TINYINT NOT NULL,
  `reset_password_token` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `broj_telefona_UNIQUE` (`broj_telefona` ASC) VISIBLE,
  UNIQUE INDEX `korisnicko_ime_UNIQUE` (`korisnicko_ime` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`donacija`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`donacija` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `termin_preuzimanja` DATETIME NOT NULL,
  `adresa` VARCHAR(50) NOT NULL,
  `broj_telefona` CHAR(11) NOT NULL,
  `napomena` MEDIUMTEXT NULL DEFAULT NULL,
  `prevoz` TINYINT NOT NULL,
  `arhivirana` TINYINT NOT NULL,
  `datum_doniranja` DATE NULL DEFAULT NULL,
  `korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idDonacije_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_donacija_korisnik1_idx` (`korisnik_id` ASC) VISIBLE,
  CONSTRAINT `fk_donacija_korisnik1`
    FOREIGN KEY (`korisnik_id`)
    REFERENCES `bl_donate`.`korisnik` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`jedinica_mjere`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`jedinica_mjere` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tip` VARCHAR(10) NOT NULL,
  `skracenica` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `tip_UNIQUE` (`tip` ASC) VISIBLE,
  UNIQUE INDEX `skracenica_UNIQUE` (`skracenica` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`kategorija_proizvoda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`kategorija_proizvoda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv_kategorije` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idKategorijeProizvoda_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`proizvod`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`proizvod` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(50) NOT NULL,
  `rok_upotrebe` DATE NULL DEFAULT NULL,
  `kategorija_proizvoda_id` INT NOT NULL,
  `jedinica_mjere_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idProizvoda_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_proizvod_kategorija_proizvoda1_idx` (`kategorija_proizvoda_id` ASC) VISIBLE,
  INDEX `fk_proizvod_jedinica_mjere1_idx` (`jedinica_mjere_id` ASC) VISIBLE,
  CONSTRAINT `fk_proizvod_jedinica_mjere1`
    FOREIGN KEY (`jedinica_mjere_id`)
    REFERENCES `bl_donate`.`jedinica_mjere` (`id`),
  CONSTRAINT `fk_proizvod_kategorija_proizvoda1`
    FOREIGN KEY (`kategorija_proizvoda_id`)
    REFERENCES `bl_donate`.`kategorija_proizvoda` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`donacija_stavka`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`donacija_stavka` (
  `kolicina` DECIMAL(6,2) NOT NULL,
  `donacija_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `proizvod_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_donacija_stavka_proizvod1_idx` (`proizvod_id` ASC) VISIBLE,
  INDEX `fk_donacija_stavka_donacija1` (`donacija_id` ASC) VISIBLE,
  CONSTRAINT `fk_donacija_stavka_donacija1`
    FOREIGN KEY (`donacija_id`)
    REFERENCES `bl_donate`.`donacija` (`id`),
  CONSTRAINT `fk_donacija_stavka_proizvod1`
    FOREIGN KEY (`proizvod_id`)
    REFERENCES `bl_donate`.`proizvod` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`obavjestenje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`obavjestenje` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sadrzaj` MEDIUMTEXT NULL DEFAULT NULL,
  `procitano` TINYINT NULL DEFAULT NULL,
  `korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_obavjestenje_korisnik1_idx` (`korisnik_id` ASC) VISIBLE,
  CONSTRAINT `fk_obavjestenje_korisnik1`
    FOREIGN KEY (`korisnik_id`)
    REFERENCES `bl_donate`.`korisnik` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`oglas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`oglas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datum_objave` DATE NULL DEFAULT NULL,
  `sadrzaj` MEDIUMTEXT NOT NULL,
  `prevoz` TINYINT NOT NULL,
  `namirnice` TINYINT NOT NULL,
  `korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idOglasa_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_oglas_korisnik1_idx` (`korisnik_id` ASC) VISIBLE,
  CONSTRAINT `fk_oglas_korisnik1`
    FOREIGN KEY (`korisnik_id`)
    REFERENCES `bl_donate`.`korisnik` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`rezervacija`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`rezervacija` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `datum_rezervacije` DATE NULL DEFAULT NULL,
  `arhivirana` TINYINT NULL DEFAULT NULL,
  `korisnik_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idRezervacije_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_rezervacija_korisnik1_idx` (`korisnik_id` ASC) VISIBLE,
  CONSTRAINT `fk_rezervacija_korisnik1`
    FOREIGN KEY (`korisnik_id`)
    REFERENCES `bl_donate`.`korisnik` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `bl_donate`.`rezervacija_stavka`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bl_donate`.`rezervacija_stavka` (
  `kolicina` DECIMAL(6,2) NOT NULL,
  `rezervacija_id` INT NOT NULL,
  `donacija_stavka_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_rezervacija_stavka_rezervacija1_idx` (`rezervacija_id` ASC) VISIBLE,
  INDEX `fk_rezervacija_stavka_donacija_stavka1_idx` (`donacija_stavka_id` ASC) VISIBLE,
  CONSTRAINT `fk_rezervacija_stavka_donacija_stavka1`
    FOREIGN KEY (`donacija_stavka_id`)
    REFERENCES `bl_donate`.`donacija_stavka` (`id`),
  CONSTRAINT `fk_rezervacija_stavka_rezervacija1`
    FOREIGN KEY (`rezervacija_id`)
    REFERENCES `bl_donate`.`rezervacija` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
