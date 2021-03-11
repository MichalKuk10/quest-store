package service;

import DAO.ArtifactDAO;
import exception.ReadException;
import model.Artifact;

import java.util.List;

public class ArtifactService {
    private final ArtifactDAO artifactDAO;

    public ArtifactService(ArtifactDAO artifactDAO) {
        this.artifactDAO = artifactDAO;
    }

    public List<Artifact> callArtifactUsageChange(String[] booleansArray, List<Artifact> artifactsList) throws ReadException {
        int count = 0;
        if (booleansArray == null || artifactsList == null || booleansArray.length == 0 || booleansArray.length != artifactsList.size()) {
            throw new IllegalArgumentException();
        }
        while (count < artifactsList.size()) {
            boolean isUsed = booleansArray[count].toUpperCase().trim().equals("USED");
            Artifact artifact = artifactsList.get(count);
            if (isUsed != artifact.isUsed()) {
                artifactDAO.markIfArtifactUsed(artifact.getId(), isUsed);
                artifact.setUsed(isUsed);
            }
            count++;
        }
        return artifactsList;
    }
}
