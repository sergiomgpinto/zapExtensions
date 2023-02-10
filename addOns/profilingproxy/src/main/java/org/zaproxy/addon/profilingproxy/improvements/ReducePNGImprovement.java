package org.zaproxy.addon.profilingproxy.improvements;

import com.tinify.Tinify;
import org.zaproxy.addon.profilingproxy.Message;

public class ReducePNGImprovement implements Improvement {

    @Override
    public ImprovementDTO getResult(Message data) {

        ImprovementDTO improvementDTO = new ImprovementDTO(false,
                false,
                true);
        improvementDTO.setImprovementIsPossible(false);

        if (!data.isPNG()) {
            return improvementDTO;
        }

        byte[] smallerImage;
        String API_KEY = "CX73NClwwp6gJbkBqngsM2bqH6t9sMM6";

        Tinify.setKey(API_KEY);

        try {
            smallerImage = Tinify.fromBuffer(data.getResponseBody()).toBuffer();
        } catch (Exception e) {
            return improvementDTO;
        }
        int difference = Math.max(data.getResponseBody().length - smallerImage.length, 0);

        if (difference != 0) {
            improvementDTO.setImprovementIsPossible(true);
            improvementDTO.setSizeReduction(difference);
            improvementDTO.setUrl(data.getUrl());
        }
        return improvementDTO;
    }
}
