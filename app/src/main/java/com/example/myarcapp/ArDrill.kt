package com.example.myarcapp


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.isGone
import com.example.myarcapp.databinding.ActivityArDrillBinding
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode

class ArDrill : AppCompatActivity() {
    private lateinit var binding : ActivityArDrillBinding
    private lateinit var modelNode: ArModelNode
//    private var modelNode: ArModelNode? = null

    private lateinit var sceneView: ArSceneView
    private var modelfile: String? = null
    private var modelLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArDrillBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        sceneView = binding.sceneView as ArSceneView

        modelfile = intent.getStringExtra("data")
        if (modelfile.isNullOrEmpty()) {
            Toast.makeText(this, "Model is empty", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Correct lifecycle management. This line is enough.
        lifecycle.addObserver(sceneView)

        val placebutton = binding.placeDrill
        modelNode = ArModelNode().apply {
            loadModelGlbAsync(modelfile.toString()) {
                if (::sceneView.isInitialized && sceneView.renderer != null) {
                    scale = dev.romainguy.kotlin.math.Float3(0.3f, 0.3f, 0.3f)
                    sceneView.planeRenderer.isVisible = true
                    modelLoaded = true
                    placebutton.isGone = false
                }
            }
            onAnchorChanged = { anchor ->
                placebutton.isGone = anchor != null
            }
        }
        sceneView.addChild(modelNode!!)

        placebutton.setOnClickListener {
//            if (::modelNode.isInitialized) {
//                modelNode.anchor()
//                sceneView.planeRenderer.isVisible = false
//            }
            if (::modelNode != null) {
                modelNode!!.anchor()
                sceneView.planeRenderer.isVisible = false
            }
        }

        binding.back.setOnClickListener {
            goback()
        }
    }

    private fun goback() {
        if (!isFinishing) {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun onDestroy() {
        // It's still a good idea to perform a final, safe cleanup
        try {
            super.onDestroy()
        } catch (e: Exception) {
            // We can gracefully ignore this exception if the scene is already gone.
        }
    }
}


