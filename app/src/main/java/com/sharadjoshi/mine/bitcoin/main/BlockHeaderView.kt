package com.sharadjoshi.mine.bitcoin.main

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.sharadjoshi.mine.bitcoin.R
import com.sharadjoshi.mine.bitcoin.data.BlockHeader
import kotlinx.android.synthetic.main.block_header_view.view.*

class BlockHeaderView : ConstraintLayout {
    private var container: View? = null

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        container = View.inflate(context, R.layout.block_header_view, null)
        container?.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        addView(container)
    }

    fun setup(blockHeader: BlockHeader) {
        with (blockHeader) {
            block_version_value.text = version.toString()
            block_timestamp_value.text = timestamp.toString()
            block_prevhash_value.text = prevBlockhash
            block_merkel_root_value.text = merkleRoot
            block_difficulty_value.text = nBits.toString()
            block_nonce_value.text = nonce.toString()
        }
    }
}
