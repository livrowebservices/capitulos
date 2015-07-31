package br.com.livroandroid;

import android.test.AndroidTestCase;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import br.com.livrowebservices.carros.R;
import br.com.livrowebservices.carros.domain.CarroService;
import br.com.livrowebservices.carros.domain.ResponseWithURL;
import livroandroid.lib.utils.IOUtils;

/**
 * Created by ricardo on 30/07/15.
 */
public class CarroServiceTest extends AndroidTestCase {

    public void testUploadFotoTest() throws IOException {
        String base64 = "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";

        // Converte arquivo para Base64 para testar
        InputStream in = getContext().getResources().openRawResource(R.raw.donald);
        byte[] bytes = IOUtils.toBytes(in);
        base64 = Base64.encodeToString(bytes,Base64.NO_WRAP);
        Log.d("test","base64 a > " + base64);

        // Mickey
        //base64 = "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2Zm148pPqml0D6opyzcmYqFQJGTjWB62pbAsq7irXhiwfDQazzjkfYehZM5s6fK62zaZlzs35+9Nbo0/ec6lxw3n7l4roq/to3IMmx17Lnv3/U+9+rdkcraomv8X63OTHhNzb76Ky2+9vim4c++ptWM5ZnydO6k8HLBsqYtN5DRgnGJwKPyidMqifZGyeIavlAZpK6RVoKCgZ8j2Nm4qvDPcpe+sd527f3PpWX0P6IQ2P19hOroOFrtfcZTZNHqbqyj+u4B889Q3cYyQA6MzVuHQ7+rca+O5/cyfqfuu3im0bmh47ND6vok4TA9GzeTaisC4uGvKHGG7Qji2kVoamVcFrXLJlNXX1+Ot1mn1zpA2b9DpLL9wxcaR733zLjlu37/IF2pOZF3HGCUsS7+z1y7vvv9bV55M1NVi6XBY3rd/UIQnvCOPDORE5O418i8PmpbyoNw/WG7tWdcc2LmqjMryoOWEpZABxyDsPJYsnfchBNpYFHzIeSEzktFef1+qs601/Up/0n5uuE4/N+bgg9se2XCE8o++wq6rGvbujrar5I7viJeePMkMVryjIzTxKbHlv1x1Ys4HguIAvcG6Xj8pxj5rGe/HF+iFp3yaNw7bKEhU6T/O2UOsGW2ClSIU7xsqJFK+Cvm2doVjpI3wQlKmqyPWwDLPGu487ECv8fRWkXpwX3P9t2YR/+J14vUje+VpdSeIFWKhAq2Q+7779qkhEBJpWcL5fu5877rogeJQ90YLvVh/T87zb110r529yYlne60J+ZycHJT+1Fk77XB0OBwc5+eTaD9fDOydAJYbJRApo2+w49m1b7/1+2xHdInq0mu/91oy85mu75sTm5526hpepzvt6YZff17t9fUfm+4XFohnd31F9oMXBT77MWSo/j+LfYmTC+vZBgAAAEd0RVh0Y29tbWVudABGaWxlIHNvdXJjZTogaHR0cDovL2VuLndpa2lwZWRpYS5vcmcvd2lraS9GaWxlOk1pY2tleV9Nb3VzZS5wbmfLB/CvAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDE0LTAxLTI0VDE5OjM5OjM1KzAwOjAwUu0exgAAACV0RVh0ZGF0ZTptb2RpZnkAMjAxNC0wMS0yNFQxOTozOTozNSswMDowMCOwpnoAAABGdEVYdHNvZnR3YXJlAEltYWdlTWFnaWNrIDYuNi45LTcgMjAxMi0wOC0xNyBRMTYgaHR0cDovL3d3dy5pbWFnZW1hZ2ljay5vcmecvblIAAAAGHRFWHRUaHVtYjo6RG9jdW1lbnQ6OlBhZ2VzADGn/7svAAAAGHRFWHRUaHVtYjo6SW1hZ2U6OmhlaWdodAAyNTBP/eL8AAAAF3RFWHRUaHVtYjo6SW1hZ2U6OldpZHRoADI1MJNRsXEAAAAZdEVYdFRodW1iOjpNaW1ldHlwZQBpbWFnZS9wbmc/slZOAAAAF3RFWHRUaHVtYjo6TVRpbWUAMTM5MDU5MjM3NZ1uxl8AAAATdEVYdFRodW1iOjpTaXplADcxLjdLQkKnNhSfAAAAM3RFWHRUaHVtYjo6VVJJAGZpbGU6Ly8vdG1wL2xvY2FsY29weV82ZDI2YmJkODMwZTAtMS5wbmfUxjQCAAAAAElFTkSuQmCC";
        //Log.d("test","base64 b > " + base64);

        ResponseWithURL response = CarroService.postFotoBase64(getContext(), "donald.png",base64);
        Log.d("test","test > " + response);
    }
}