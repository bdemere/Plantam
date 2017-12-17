package com.cpsc310proj.babib.plantam.Enums;

/**
 * Created by anhthuynguyen
 */

public enum Location {
    MATHER("Mather Hall","41.746631","-72.692567"),
    MECC("Math, CS and Engineering Center","41.744430994","-72.6911037"),
    MCCOOK("McCook Hall","41.745779","-72.691427"),
    CINESTUDIO("CineStudio","41.747598", "-72.691074"),
    LSC("Life Science Center","41.7449902","-72.6905868"),
    AUSTIN("Austin Arts Center","41.7462655","-72.6912415"),
    LIBRARY("Raether Library","41.7473637","-72.6907805"),
    VERNON("Vernon Social","41.7511516","-72.69147"),
    FERRIS("Ferris Athletic Center","41.7466256","-72.6892173"),
    KOEPPEL("Koeppel Rink","41.7435244","-72.6892147"),
    CUSTOM("Unnamed location on campus","41.7406671","-72.7078458"); //Trinity as default

    private String location_name;
    private String location_x;
    private String location_y;

    private Location(String type_name, String geo_x, String geo_y){
        this.location_name = type_name;
        this.location_x = geo_x;
        this.location_y = geo_y;
    }

    public String getGeo() {
        return "<" + location_x + ">,<" + location_y + ">";
    }
    public String toString(){
        return location_name;
    }
}

