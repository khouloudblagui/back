package com.example.parameterization.Implementation;

import com.example.parameterization.Entity.DocTemp;
import com.example.parameterization.Enum.ElementType;
import com.example.parameterization.Enum.TemplateFormat;
import com.example.parameterization.Repository.DocTempRepo;
import com.example.parameterization.Service.DocTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocTempServiceImpl implements DocTempService {

    @Autowired
    private DocTempRepo docTempRepository;

    public void uploadDocument(MultipartFile ifile, String ititle, String idescription, TemplateFormat iformat,
                               ElementType iparentElementType, int iparentElementKey) throws IOException {
        DocTemp docTemp = new DocTemp();
        docTemp.setTemplateDocTitle(ititle);
        docTemp.setTemplateDoc_Desc(idescription);
        docTemp.setTemplateDoc_Format(iformat);
        docTemp.setFileData(ifile.getBytes());
        docTemp.setTemplateDoc_PrntElmntTp(iparentElementType);
        docTemp.setTemplateDoc_PrntElmntKy(iparentElementKey);
        docTempRepository.save(docTemp);
    }
    @Override
    public DocTemp getDocumentById(Long iId) {
        return this.docTempRepository.findById(iId).orElse(null);
    }

    @Override
    public List<DocTemp> getAllDocuments() {
        return this.docTempRepository.findAll();
    }

    @Override
    public void deleteDocument(Long iId) {
        this.docTempRepository.deleteById(iId);
    }

    @Override
    public void updateDocument(Long iId, String ititle, String idescription, TemplateFormat iformat,
                               ElementType iparentElementType, int iparentElementKey) throws IOException {
        DocTemp docTemp = docTempRepository.findById(iId).orElse(null);
        if (docTemp != null) {
            docTemp.setTemplateDocTitle(ititle);
            docTemp.setTemplateDoc_Desc(idescription);
            docTemp.setTemplateDoc_Format(iformat);
            docTemp.setTemplateDoc_PrntElmntTp(iparentElementType);
            docTemp.setTemplateDoc_PrntElmntKy(iparentElementKey);
            docTempRepository.save(docTemp);
        }
    }
    @Override
    public byte[] downloadDocument(Long id) {
        DocTemp docTemp = docTempRepository.findById(id).orElse(null);
        if (docTemp != null) {
            return docTemp.getFileData();
        }
        return null;
    }

    public boolean checkTitleExists(String title) {
        return docTempRepository.existsByTemplateDocTitle(title);
    }
}
