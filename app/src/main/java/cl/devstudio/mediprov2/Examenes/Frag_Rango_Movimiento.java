package cl.devstudio.mediprov2.Examenes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import cl.devstudio.mediprov2.Frag_Menu.Frag_Examen;
import cl.devstudio.mediprov2.Model.DeviceItem;
import cl.devstudio.mediprov2.R;

public class Frag_Rango_Movimiento extends Fragment {
    DeviceItem newDevice;
    Fragment miFragment = null;
    View view;
    Button btn_Go, btn_guardar, buscar;
    TextView txt_Close, x, y;
    Dialog dialog;
    ImageView bluetoohPopUp, back;
    ListView listViewPaired, listViewNew;
    ArrayList listaEmparejados = new ArrayList();
    ArrayList listaAddressEmp = new ArrayList();
    ArrayList list = new ArrayList();
    ArrayAdapter adapter = null;
    boolean validar = true;
    boolean validarbtn = true;
    boolean r;
    String result, rut;

    //-------------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String para la direccion MAC
    String address = null;
    //-------------------------------------------

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_examen_rango, container, false);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_popup);
        bluetoohPopUp = view.findViewById(R.id.id_bluetoothPopUp);
        txt_Close = dialog.findViewById(R.id.txt_close);
        buscar = dialog.findViewById(R.id.id_buscarBluetooth);
        btn_Go = view.findViewById(R.id.btn_examensGo);
        x = view.findViewById(R.id.id_dato_X);
        y = view.findViewById(R.id.id_dato_Y);
        back = view.findViewById(R.id.toolbar_back);
        btn_guardar = view.findViewById(R.id.btn_guardarRango);
        setTitle();
        //-----------Switch button------------------------------------------------------------
        bluetoohPopUp.setOnClickListener(new onClick());
        txt_Close.setOnClickListener(new onClick());
        buscar.setOnClickListener(new onClick());
        btn_Go.setOnClickListener(new onClick());
        btn_guardar.setOnClickListener(new onClick());
        //------------------------------------------------------------------------------------

        ////////////////Manejador de mensajes y llamara al metodo Run///////////////////////////////
        bluetoothIn = new Handler() {
            @SuppressLint("SetTextI18n")
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {

                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);
                    // int endOfLineIndex = DataStringIN.indexOf("#");
                    r = readMessage.contains("#");
                    if (r) {
                        String[] parts3 = readMessage.split("/");
                        String part3 = parts3[0];
                        String[] parts = part3.split("#");
                        String part1 = parts[0];
                        String part2 = parts[1];
                        x.setText("X: " + part1);
                        y.setText("Y: " + part2);
                    }
                }
            }
        };
        ///////////////////////////////////////////////////
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        return view;
    }

    public void capturaDatos() {
        String datox,datoy;
        datox = x.getText().toString();
        datoy = y.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        Toast.makeText(getContext(), " "+datox+" "+datoy+" "+fecha+" "+rut, Toast.LENGTH_SHORT).show();

    }

    private void setTitle() {
        SharedPreferences prefs =
                getActivity().getSharedPreferences("infoPaciente", Context.MODE_PRIVATE);

        String nombre = prefs.getString("names", "por_defecto@email.com");
        rut = prefs.getString("rut", "rut");
        TextView titulo;
        titulo = view.findViewById(R.id.tb_title);
        titulo.setText(nombre);
        back.setVisibility(View.VISIBLE);
    }

    public class onClick implements View.OnClickListener {
        @SuppressLint({"HandlerLeak", "SetTextI18n"})
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.id_bluetoothPopUp:
                    ShowPopUp();
                    break;
                case R.id.txt_close:
                    dialog.dismiss();
                    break;
                case R.id.id_buscarBluetooth:
                    if (validar) {
                        validar = false;
                        buscar.setText("DETENER");
                        list.clear();
                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        getActivity().registerReceiver(bReciever, filter);
                        btAdapter.startDiscovery();
                    } else {
                        validar = true;
                        buscar.setText("BUSCAR");
                        btAdapter.cancelDiscovery();
                    }
                    break;
                case R.id.btn_examensGo:
                    if (validarbtn) {
                        validarbtn = false;
                        if (address == null) {
                            Toast.makeText(getContext(), "No se encuentra dispositivo conectado"
                                    , Toast.LENGTH_SHORT).show();
                        } else {
                            MyConexionBT.write("1");

                        }
                        btn_Go.setText("DETENER");
                    } else {
                        validarbtn = true;
                        btn_Go.setText("INICIAR");
                        MyConexionBT.write("0");
                        /*
                        try {
                            btSocket.close();
                        } catch (IOException e) {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                    break;
                case R.id.tb_title:
                    miFragment = new Frag_Examen();
                    getFragmentManager().beginTransaction().
                            replace(R.id.container_fragment, miFragment, null).
                            addToBackStack(null).commit();
                    break;
                case R.id.btn_guardarRango:
                    capturaDatos();
                    break;

            }
        }
    }

    public void ShowPopUp() {
        listViewPaired = dialog.findViewById(R.id.id_listaBT_Paired);
        listViewNew = dialog.findViewById(R.id.id_listaBT_New);
        dialog.show();
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                address = newDevice.getAddress();
                capturarMac();
                dialog.dismiss();
            }
        });
        listViewPaired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                address = listaAddressEmp.get(position).toString();
                capturarMac();
                dialog.dismiss();
            }
        });
        searchPairedDevices();
    }

    private void searchPairedDevices() {
        listaEmparejados.clear();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        for (BluetoothDevice bt : pairedDevices) {
            listaEmparejados.add(bt.getName());
            listaAddressEmp.add(bt.getAddress());
        }
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaEmparejados);
        listViewPaired.setAdapter(adapter);
    }

    //Busca y muestra dispositivos nuevos
    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Create a new device item
                newDevice = new DeviceItem(device.getName(), device.getAddress(), "false");
                // Add it to our adapter
                if (newDevice.getDeviceName() == null) {
                    list.add(newDevice.getAddress());
                } else {
                    list.add(newDevice.getDeviceName());
                }
                adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                listViewNew.setAdapter(adapter);
            }
        }
    };

    //Verifica si el smartphone cuenta con soporte bluetooth
    public void verificarSoporteBluetooth() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        //Identificar si el telefono soporta bluetooth
        if (btAdapter == null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("No es compatible")
                    .setMessage("Tu smartphone no soporta bluetooth")
                    .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    //Verfica el estado del bluetooth
    public void verficarBluetooth() {
        if (!btAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
    }

    //Nuevo codigo ----------------------------------------------------------------------------


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    public void capturarMac() {
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexión con el socket Bluetooth.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
            }
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        try { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {
        }
    }

    //Comprueba que el dispositivo Bluetooth Bluetooth está disponible y solicita que se active si está desactivado
    private void VerificarEstadoBT() {

        if (btAdapter == null) {
            Toast.makeText(getContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //Envio de trama
        public void write(String input) {
            try {
                mmOutStream.write(input.getBytes());
            } catch (IOException e) {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
            }
        }
    }

}
