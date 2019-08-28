//
//  ViewController.swift
//  MPPSample
//
//  Created by Kota Tomoyasu on 2019/08/27.
//  Copyright © 2019 k-tomoyasu. All rights reserved.
//

import UIKit
import common

class ViewController: UIViewController {
    @IBOutlet weak var searchButton: UIButton!
    @IBOutlet weak var searchText: UITextField!
    @IBOutlet weak var resultText: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let lastSearched = Preference().get(key: "LAST_SEARCHED")
        searchText.text = lastSearched
    }

    @IBAction func tapSearch(_ sender: Any) {
        ApiClient().searchRepository(
            value: searchText.text ?? "",
            onSuccess: { repos -> Void in
                if let repository = repos.items.first {
                    self.resultText.text = "Top of Stargazer is:" + repository.name
                }
            },
            onError: { e -> Void in
                print(e.message)
            }
        )
    }
    
}

