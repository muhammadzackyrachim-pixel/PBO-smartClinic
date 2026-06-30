package util;

public class TestModel {
    public static void main(String[] args) {
        try {
            System.out.println("Testing ONNX Model Loader...");
            ONNXModelLoader loader = new ONNXModelLoader("/diabetes_decision_tree.onnx");
            
            // Typical high-risk features for Pima Diabetes:
            // pregnancies=8, glucose=183, bp=64, skin=0, insulin=0, bmi=23.3, pedigree=0.672, age=32
            float[] highRiskFeatures = new float[] { 8f, 183f, 64f, 0f, 0f, 23.3f, 0.672f, 32f };
            System.out.println("High Risk Prediction: " + loader.predictDiabetes(highRiskFeatures));
            
            // Typical low-risk features:
            // pregnancies=1, glucose=85, bp=66, skin=29, insulin=0, bmi=26.6, pedigree=0.351, age=31
            float[] lowRiskFeatures = new float[] { 1f, 85f, 66f, 29f, 0f, 26.6f, 0.351f, 31f };
            System.out.println("Low Risk Prediction: " + loader.predictDiabetes(lowRiskFeatures));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
