package com.example.gwen.vfacture;

/**
 * Created by gwen on 27/01/2016.
 */
public class Prestation {
    private String code_prod;
    private int code_four;
    private String code_pays;
    private String code_pv;
    private int voucher;
    private String libelle1;
    private String libelle2;
    private String libelle3;
    private String libelle4;
    private Double prix;
    private int quantite;
    private Double total;

    //Information supp (fournisseur,pays,libelle)
    private String nm_pays;
    private String nm_four;
    private String libelle;

    //Information pour l'affichage par rapport au "", "*" et "#"
    //Possibilité : visible, partielle, cache
    private String visibility;

    public Prestation(String code_prod, int code_four, String code_pays, String code_pv, int voucher,
                      String libelle1, String libelle2, String libelle3, String libelle4, Double prix, int quantite,
                      String nm_pays, String nm_four, String libelle, String visibility, Double total) {
        this.code_prod = code_prod;
        this.code_four = code_four;
        this.code_pays = code_pays;
        this.code_pv = code_pv;
        this.voucher = voucher;
        this.libelle1 = libelle1;
        this.libelle2 = libelle2;
        this.libelle3 = libelle3;
        this.libelle4 = libelle4;
        this.prix = prix;
        this.quantite = quantite;

        this.nm_pays = nm_pays;
        this.nm_four = nm_four;
        this.libelle = libelle;

        this.visibility = visibility;
        this.total = total;
    }


    public String getCode_prod(){
        return code_prod;
    }
    public int getCode_four(){
        return code_four;
    }
    public String getCode_pays(){
        return  code_pays;
    }
    public String getCode_pv(){
        return code_pv;
    }
    public int getVoucher(){
        return voucher;
    }
    public String getLibelle1(){
        return libelle1;
    }
    public String getLibelle2(){
        return libelle2;
    }
    public String getLibelle3(){
        return libelle3;
    }
    public String getLibelle4(){
        return libelle4;
    }
    public Double getPrix(){
        return prix;
    }
    public int getQuantite(){
        return quantite;
    }
    public String getNm_pays(){
        return nm_pays;
    }
    public String getNm_four(){
        return nm_four;
    }
    public String getLibelle(){
        return libelle;
    }
    public String getVisibility(){
        return visibility;
    }
    public Double getTotal() {
        return total;
    }

    public void setCode_prod(String code_prod) {
        this.code_prod = code_prod;
    }

    public void setCode_four(int code_four) {
        this.code_four = code_four;
    }

    public void setCode_pays(String code_pays) {
        this.code_pays = code_pays;
    }

    public void setCode_pv(String code_pv) {
        this.code_pv = code_pv;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public void setLibelle1(String libelle1) {
        this.libelle1 = libelle1;
    }

    public void setLibelle2(String libelle2) {
        this.libelle2 = libelle2;
    }

    public void setLibelle3(String libelle3) {
        this.libelle3 = libelle3;
    }

    public void setLibelle4(String libelle4) {
        this.libelle4 = libelle4;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void cumulPrix(Double unPrix){
        this.prix = this.prix + unPrix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setNm_pays(String nm_pays) {
        this.nm_pays = nm_pays;
    }

    public void setNm_four(String nm_four) {
        this.nm_four = nm_four;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setVisibility(String visibility){
        this.visibility = visibility;
    }

    public void setTotal(Double total){
        this.total = total;
    }

}
