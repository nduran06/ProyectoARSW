package edu.eci.arsw.treecore.persistence.impl;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.util.IOUtil.ProgressListener;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.WriteMode;
import org.springframework.stereotype.Service;

import edu.eci.arsw.treecore.exceptions.TreeCoreStoreException;
import edu.eci.arsw.treecore.persistence.TreeCoreStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;

/**
 * Almacenamiento usando Dropbox.
 * @author Ricardo Martinez
 */
@Service
public class TreeCoreStoreImpl implements TreeCoreStore {
    private static final String ACCESS_TOKEN = "OAn_tALL5dAAAAAAAAAANIysHzsxgb9xvKzwEZgeChMqfxPZCvPexSzT2sNUDiIU";
    private static DbxClientV2 client;

    /**
     * Constructor of TreeCoreStore
     */
    public TreeCoreStoreImpl() {
        final DbxRequestConfig config = new DbxRequestConfig("");
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    /**
     * Consultar Archivos en una ruta.
     * 
     * @param ruta Ruta de la carpeta
     * @return
     * @throws TreeCoreStoreException
     */
    @Override
    public List<String> consultar(final String path) throws TreeCoreStoreException {
        ListFolderResult result;
        List<String> contenido = new ArrayList<String>();
        String ruta = path.replace("+++", "/"); 
        try {
            result = client.files().listFolder("/"+ruta);
            while (true) {
                for (final Metadata metadata : result.getEntries()) {
                    contenido.add(metadata.getPathLower());
                }

                if (!result.getHasMore()) {
                    break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }
            final File localFile = new File("Help.mp3");
            if (localFile.exists()) {
                System.out.println(localFile.getAbsolutePath());
                // uploadFile(client,localFile,"/Help.mp3");
            }
        } catch (final ListFolderContinueErrorException e) {
            throw new TreeCoreStoreException("Error listing files of " + ruta);
        } catch (final DbxException e) {
            throw new TreeCoreStoreException("Error looking for the route: " +ruta);
        }
        return contenido;
    }

    /**
     * Cargar un archivo al contenedor.
     * @param localFile Archivo
     * @param path Ruta donde se va a colocar.
     * @throws TreeCoreStoreException
     */
    @Override
    public void uploadFile(final File localFile, final String path) throws TreeCoreStoreException {
        try (InputStream in = new FileInputStream(localFile)) {
            client.files().deleteV2(path);
            final ProgressListener progressListener = l -> printProgress(l, localFile.length());
            final FileMetadata metadata = client.files().uploadBuilder(path)
                .withMode(WriteMode.ADD)
                .withClientModified(new Date(localFile.lastModified()))
                .uploadAndFinish(in, progressListener);

            System.out.println(metadata.toStringMultiline());
        } catch (final DbxException  ex) {
            throw new TreeCoreStoreException("Error uploading to Dropbox: " + ex.getMessage());

        } catch (final IOException ex) {
            throw new TreeCoreStoreException("Error reading from file \"" + localFile + "\": " + ex.getMessage());
        }
    }
    
    private static void printProgress(final long uploaded, final long size) {
        System.out.printf("Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
    }
    
    /**
     * Crea un link de descarga del archivo indicado.
     * 
     * @param path Ruta del archivo.
     * @return
     * @throws TreeCoreStoreException
     */
    @Override
    public List<String> downloadFile(String path) throws TreeCoreStoreException {
        String ruta = path.replace("+++", "/"); 
        List<String> datos = new ArrayList<String>();
		try {
            GetTemporaryLinkResult DownloadLinkData = client.files().getTemporaryLink("/"+ruta);
            datos.add(DownloadLinkData.getMetadata().getName());
            datos.add(DownloadLinkData.getLink());
		} catch (final DbxException e) {
			throw new TreeCoreStoreException("Error downloading from Dropbox: " + e.getMessage());
        }
        return datos;
    
    }
}