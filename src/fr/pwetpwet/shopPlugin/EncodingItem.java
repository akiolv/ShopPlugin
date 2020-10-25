package fr.pwetpwet.shopPlugin;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.Base64;


public class EncodingItem {

    private ItemStack item;

    public EncodingItem(){this.item = null;}

    public EncodingItem(ItemStack item){
        this.item = item;
    }

    public String codeItem(){

        String encodedObject = null;

        try{
            //Serialize the item(turn it into byte stream)
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
            os.writeObject(item);
            os.flush();

            byte[] serializedObject = io.toByteArray();

            //Encode the serialized object into to the base64 format
            encodedObject = new String(Base64.getEncoder().encode(serializedObject));

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return encodedObject;
    }

    public ItemStack decodeItem(String encodedObject){

        ItemStack newItem = null;

        try {
            //decode string into raw bytes
            byte[] serializedObject = Base64.getDecoder().decode(encodedObject);

            //Input stream to read the byte array
            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            //object input stream to serialize bytes into objects
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);

            newItem = (ItemStack) is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newItem;

    }
}
