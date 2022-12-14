package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.fileHandler.DeleteFile;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.fileHandler.WriteFile;
import dev.silvia.wechattrade.service.ISystemManageService;
import dev.silvia.wechattrade.vo.AdsVo;
import dev.silvia.wechattrade.vo.PrincipleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SystemManageServiceImpl extends ServiceImpl<UserDao, User> implements ISystemManageService {
    @Autowired
    private ReadFile readFile;
    @Autowired
    private WriteFile writeFile;
    @Autowired
    private DeleteFile deleteFile;

    private String help_url = FileDirector.HELP_URL;


    @Override
    public Integer createHelpCatalog(String name) {
        return writeFile.newHelpCatalog(name);
    }

    @Override
    public Integer createHelp(String catalog, String question, String answer) {
        if(readFile.checkHelpQuestion(catalog, question)){
            return 402; // 已存在問題，請換一個或直接更新
        }else {
            return writeFile.newHelp(catalog, question, answer);
        }
    }

    @Override
    public Integer deleteHelp(String catalog, String question) {
        return deleteFile.deleteHelp(catalog, question);
    }

    @Override
    public Integer deleteHelpCatalog(String catalog) {
        if(readFile.checkHelpExist(catalog)){
            return deleteFile.deleteHelpCatalog(catalog);
        }
        return 400; // 還有文件存在不可刪除資料夾
    }

    @Override
    public Integer updateHelp(String catalog, String question, String newAnswer) {
        if(readFile.checkHelpQuestion(catalog,question)){
            // 問題存在方可修改
            return writeFile.newHelp(catalog,question,newAnswer);
        }
        return 400;
    }

    @Override
    public Integer updateHelpCatalog(String catalog, String newCatalog) {
        return writeFile.updateHelpCatalog(catalog, newCatalog);
    }

    @Override
    public List<String> showHelpCatalog() {
        List<String> catalogs = readFile.getSubFileNames(help_url);
        return catalogs;
    }

    @Override
    public List<String> showHelpQuestion(String catalog) {
        String root = help_url +"/"+catalog;
        List<String> questions = readFile.getSubFileNames(root);
        if(questions != null || !questions.isEmpty()){
            for(int i = 0 ; i < questions.size(); i++){
                questions.set(i, questions.get(i).replaceAll(".txt", ""));
            }
        }
        return questions;
    }

    @Override
    public String showHelpAnswer(String catalog, String question) {
        String filePath = help_url +"/"+catalog+"/"+question+".txt";
        String answer = readFile.readHelpFile(filePath);
        return answer;
    }

    @Override
    public Integer createUserPrinciple(String version, String content) {
        // 確認該版本是否存在
        if(readFile.checkPrincipleVersion(version)){
            return 404; // 該版本已存在，請換一個
        }
        return writeFile.createPrinciple(version, content);
    }

    @Override
    public Integer deleteUserPrinciple(String version) {
        return deleteFile.deletePrinciple(version);
    }

    @Override
    public Integer updateUserPrinciple(String version, String content) {
        if(readFile.checkPrincipleVersion(version)){
            return writeFile.createPrinciple(version, content);
        }
        return 404; // 該版本不存在，請換一個
    }

    @Override
    public PrincipleVo showPrinciple(String version) {
        String filePath = FileDirector.USER_PRINCIPLE_URL+"/"+version+".txt";
        PrincipleVo principleVo = readFile.readPrinciple(filePath);
        return principleVo;
    }

    @Override
    public List<PrincipleVo> showAllPrinciple() {
        List<String> principle_urls = readFile.getAllPrincipleUrl();
        if(principle_urls == null || principle_urls.isEmpty()){
            return null;
        }
        List<PrincipleVo> principleVos = new ArrayList<>();
        for(String principle_url : principle_urls){
            PrincipleVo principleVo = readFile.readPrinciple(principle_url);
            principleVos.add(principleVo);
        }
        return principleVos;
    }

    @Override
    public PrincipleVo showNewestPrinciple() {
        if(readFile.getNewestFile(FileDirector.USER_PRINCIPLE_URL) == null){
            return null;
        }
        String version = readFile.getNewestFile(FileDirector.USER_PRINCIPLE_URL);
        PrincipleVo principleVo = readFile.readPrinciple(FileDirector.USER_PRINCIPLE_URL+"/"+version+".txt");
        return principleVo;
    }

    @Override
    public Integer postAdsPicture(MultipartFile file) {
        // 檢查是否可上傳，圖片數量不可超過4
        // 若可以則上傳，不行則返回
        return writeFile.save_ads_pic(file);
    }

    @Override
    public Integer deleteAdsPicture(Integer index) {
        // 刪除某一圖片
        return deleteFile.delete_ads_pic(index);
    }

    @Override
    public Integer updateAdsPicture(Integer index, MultipartFile file) {
        // 刪掉原本的在寫入新的
        if(deleteFile.delete_ads_pic(index) == 204){
            return writeFile.save_ads_pic(file);
        }
        return 402; // 刪除失敗
    }

    @Override
    public List<AdsVo> showAllAdsPicture() {
        List<Map<String,Object>> mapList = readFile.readAdsPictures();
        if(mapList == null || mapList.size()==0){
            return null;
        }
        List<AdsVo> adsVos = new ArrayList<>();
        for(int i = 0 ; i < mapList.size() ; i ++){
            AdsVo adsVo = new AdsVo();
            Map<String, Object> map = mapList.get(i);
            adsVo.setIndex(i);
            adsVo.setFormat(map.get("format").toString());
            adsVo.setPicture(map.get("picture").toString());
            adsVos.add(adsVo);
        }
        return adsVos;
    }

}
