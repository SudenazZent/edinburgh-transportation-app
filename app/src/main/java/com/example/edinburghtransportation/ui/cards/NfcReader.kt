package com.example.edinburghtransportation.ui.cards

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.content.Context
import android.app.Activity

class NfcReader(
    private val context: Context
) {

    private val nfcAdapter: NfcAdapter? =
        NfcAdapter.getDefaultAdapter(context)

    fun isNfcAvailable(): Boolean {
        return nfcAdapter != null
    }

    fun isNfcEnabled(): Boolean {
        return nfcAdapter?.isEnabled == true
    }

    fun startReading(
        activity: Activity,
        onTagRead: (String) -> Unit
    ) {
        nfcAdapter?.enableReaderMode(
            activity,
            { tag: Tag ->
                val tagId = tag.id

                val cardNumber = tagId.joinToString("") {
                    "%02X".format(it)
                }

                activity.runOnUiThread {
                    onTagRead(cardNumber)
                }
            },
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    fun stopReading(activity: Activity) {
        nfcAdapter?.disableReaderMode(activity)
    }
}