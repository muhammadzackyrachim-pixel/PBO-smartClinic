package util;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import ai.onnxruntime.OrtSession.Result;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class ONNXModelLoader {

    private OrtEnvironment env;
    private OrtSession session;
    private String inputName;

    public ONNXModelLoader(String modelPath) throws Exception {
        env = OrtEnvironment.getEnvironment();
        InputStream is = getClass().getResourceAsStream(modelPath);
        if (is != null) {
            byte[] modelBytes = is.readAllBytes();
            session = env.createSession(modelBytes, new OrtSession.SessionOptions());
        } else {
            session = env.createSession(modelPath, new OrtSession.SessionOptions());
        }
        
        // Dapatkan nama input pertama secara dinamis
        inputName = session.getInputNames().iterator().next();
    }

    public String predictDiabetes(float[] features) throws Exception {
        float[][] inputData = new float[][] { features };
        try (OnnxTensor tensor = OnnxTensor.createTensor(env, inputData)) {
            Map<String, OnnxTensor> inputs = Collections.singletonMap(inputName, tensor);
            try (Result results = session.run(inputs)) {
                // Mengambil output pertama (biasanya label kelas prediksi)
                Object output = results.get(0).getValue();
                
                boolean isRiskHigh = false;
                
                if (output instanceof long[]) {
                    long[] outputArr = (long[]) output;
                    isRiskHigh = (outputArr[0] == 1);
                } else if (output instanceof float[]) {
                    float[] outputArr = (float[]) output;
                    isRiskHigh = (outputArr[0] >= 0.5);
                } else if (output instanceof long[][]) {
                    long[][] outputArr = (long[][]) output;
                    isRiskHigh = (outputArr[0][0] == 1);
                } else if (output instanceof float[][]) {
                    float[][] outputArr = (float[][]) output;
                    isRiskHigh = (outputArr[0][0] >= 0.5);
                } else {
                    System.out.println("Tipe output tidak dikenali: " + output.getClass().getName());
                    // Fallback
                    return "HASIL PREDIKSI TIDAK DIKETAHUI";
                }
                
                return isRiskHigh ? "RISIKO DIABETES TINGGI" : "RISIKO DIABETES RENDAH";
            }
        }
    }
}
